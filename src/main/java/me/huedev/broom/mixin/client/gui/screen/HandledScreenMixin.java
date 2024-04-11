package me.huedev.broom.mixin.client.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {
    @Shadow
    protected abstract void drawForeground();

    @Shadow
    protected abstract Slot getSlotAt(int x, int y);

    @Shadow
    public ScreenHandler container;

    @Shadow
    protected abstract boolean isPointOverSlot(Slot slot, int x, int y);

    @Unique
    private Slot slot;

    @Unique
    Slot lastRMBSlot = null;

    @Unique
    Slot lastLMBSlot = null;

    @Unique
    int lastRMBSlotId = -1;

    @Unique
    int lastLMBSlotId = -1;

    @Unique
    private ItemStack leftClickMouseTweaksPersistentStack = null;

    @Unique
    private ItemStack leftClickPersistentStack = null;

    @Unique
    private ItemStack rightClickPersistentStack = null;

    @Unique
    private boolean isLeftClickDragMouseTweaksStarted = false;

    @Unique
    private boolean isLeftClickDragStarted = false;

    @Unique
    private boolean isRightClickDragStarted = false;

    @Unique
    private final List<Slot> leftClickHoveredSlots = new ArrayList<>();

    @Unique final List<Slot> rightClickHoveredSlots = new ArrayList<>();

    @Unique Integer leftClickItemAmount;

    @Unique Integer rightClickItemAmount;

    @Unique final List<Integer> leftClickExistingAmount = new ArrayList<>();

    @Unique final List<Integer> rightClickExistingAmount = new ArrayList<>();

    @Unique List<Integer> leftClickAmountToFillPersistent = new ArrayList<>();

    /**
     * @author DanyGames2014
     */
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawForeground()V"
            )
    )
    public void broom_cancelDrawForeground(HandledScreen instance) {
    }

    /**
     * @author DanyGames2014
     */
    @Inject(
            method = "render",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.GETFIELD,
                    target = "Lnet/minecraft/entity/player/ClientPlayerEntity;inventory:Lnet/minecraft/entity/player/PlayerInventory;"
            )
    )
    public void broom_injectDrawForeground(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        drawForeground();
    }

    /**
     * @author DanyGames2014
     */
    @Inject(
            method = "drawSlot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/texture/TextureManager;bindTexture(I)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void broom_addArmorSlotIcon(Slot slot, CallbackInfo ci) {
        if (slot.method_471() == 7355608) {
            this.minecraft.textureManager.bindTexture(this.minecraft.textureManager.getTextureId("/assets/broom/textures/gui/armor_icons.png"));
            this.drawTexture(slot.x, slot.y, 0, (slot.id - 5) * 16, 16, 16);
            GL11.glEnable(2896);
            ci.cancel();
        }
    }

    /**
     * @author telvarost
     */
    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    protected void broom_mouseClicked(int mouseX, int mouseY, int button, CallbackInfo ci) {
        isLeftClickDragMouseTweaksStarted = false;

        /* - Check if client is on a server */
        boolean isClientOnServer = minecraft.world.isRemote;

        /* - Right-click */
        if (button == 1) {
            boolean exitFunction = false;

            /* - Should click cancel Left-click + Drag */
            if (!broom_cancelLeftClickDrag(isClientOnServer)) {
                /* - Handle Right-click */
                exitFunction = broom_handleRightClick(mouseX, mouseY);
            } else {
                exitFunction = true;
            }

            if (exitFunction) {
                /* - Handle if a button was clicked */
                super.mouseClicked(mouseX, mouseY, button);
                ci.cancel();
                return;
            }
        }

        /* - Left-click */
        if (button == 0) {
            boolean exitFunction = false;

            /* - Should click cancel Right-click + Drag */
            if (!broom_cancelRightClickDrag(isClientOnServer)) {
                /* - Handle Left-click */
                ItemStack cursorStack = minecraft.player.inventory.getCursorStack();
                Slot clickedSlot = this.getSlotAt(mouseX, mouseY);
                if (cursorStack != null) {
                    exitFunction = broom_handleLeftClickWithItem(cursorStack, clickedSlot, isClientOnServer);
                } else {
                    exitFunction = broom_handleLeftClickWithoutItem(clickedSlot);
                }
            } else {
                exitFunction = true;
            }

            if (exitFunction) {
                /* - Handle if a button was clicked */
                super.mouseClicked(mouseX, mouseY, button);
                ci.cancel();
                return;
            }
        }
    }

    /**
     * @author telvarost
     */
    @Inject(method = "mouseReleased", at = @At("RETURN"), cancellable = true)
    private void broom_mouseReleasedOrSlotChanged(int mouseX, int mouseY, int button, CallbackInfo ci) {
        slot = this.getSlotAt(mouseX, mouseY);

        /* - Do nothing if mouse is not over a slot */
        if (slot == null)
            return;

        /* - Right-click + Drag logic = distribute one item from held items to each slot */
        if (  ( button == -1 )
                && ( Mouse.isButtonDown(1) )
                && (!isLeftClickDragStarted)
                && (!isLeftClickDragMouseTweaksStarted)
                && ( rightClickPersistentStack != null )
        ) {
            ItemStack slotItemToExamine = slot.getStack();

            /* - Do nothing if slot item does not match held item or if the slot is full */
            if (  (null != slotItemToExamine)
                    && (  (!slotItemToExamine.isItemEqual(rightClickPersistentStack))
                    || (slotItemToExamine.count == rightClickPersistentStack.getMaxCount())
            )
            ) {
                return;
            }

            /* - Do nothing if there are no more items to distribute */
            ItemStack cursorStack = minecraft.player.inventory.getCursorStack();
            if (null == cursorStack) {
                return;
            }

            if (!rightClickHoveredSlots.contains(slot)) {
                broom_handleRightClickDrag(slotItemToExamine);
            }
        } else {
            broom_resetRightClickDragVariables();
        }

        /* - Left-click + Drag logic = evenly distribute held items over slots */
        if (  ( button == -1 )
                && ( Mouse.isButtonDown(0) )
                && (!isRightClickDragStarted)
        ) {
            if (isLeftClickDragMouseTweaksStarted) {
                broom_handleLeftClickDragMouseTweaks();
            } else if ( leftClickPersistentStack != null ) {
                if (broom_handleLeftClickDrag()) {
                    return;
                }
            } else {
                broom_resetLeftClickDragVariables();
            }
        } else {
            broom_resetLeftClickDragVariables();
        }
    }

    /**
     * @author telvarost
     */
    @Unique
    private boolean broom_handleRightClick(int mouseX, int mouseY) {
        /* - Get held item */
        ItemStack cursorStack = minecraft.player.inventory.getCursorStack();

        /* - Handle Right-click if an item is held */
        if (null != cursorStack) {
            /* - Ensure a slot was clicked */
            Slot clickedSlot = this.getSlotAt(mouseX, mouseY);
            if (null != clickedSlot) {

                /* - Record how many items are in the slot */
                if (null != clickedSlot.getStack()) {
                    /* - Let vanilla minecraft handle right click with an item onto a different item */
                    if (!cursorStack.isItemEqual(clickedSlot.getStack())) {
                        return false;
                    }

                    rightClickExistingAmount.add(clickedSlot.getStack().count);
                } else {
                    rightClickExistingAmount.add(0);
                }

                /* - Begin Right-click + Drag */
                if (cursorStack != null && rightClickPersistentStack == null && !isRightClickDragStarted) {
                    rightClickPersistentStack = cursorStack;
                    rightClickItemAmount = rightClickPersistentStack.count;
                    isRightClickDragStarted = true;
                }

                /* - Handle initial Right-click */
                lastRMBSlotId = clickedSlot.id;
                lastRMBSlot = clickedSlot;
                boolean isShiftKeyDown = (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT));
                this.minecraft.interactionManager.clickSlot(this.container.syncId, clickedSlot.id, 1, isShiftKeyDown, this.minecraft.player);

                if (isShiftKeyDown) {
                    broom_resetRightClickDragVariables();
                }

                return true;
            }
        }

        return false;
    }

    /**
     * @author telvarost
     */
    @Unique private void broom_handleRightClickDrag(ItemStack slotItemToExamine) {
        /* - First slot is handled instantly in mouseClicked function */
        if (slot.id != lastRMBSlotId) {
            if (rightClickHoveredSlots.isEmpty())
            {
                /* - Add slot to item distribution */
                rightClickHoveredSlots.add(lastRMBSlot);
            }

            /* - Add slot to item distribution */
            rightClickHoveredSlots.add(slot);

            /* - Record how many items are in the slot */
            if (slotItemToExamine != null) {
                rightClickExistingAmount.add(slotItemToExamine.count);
            }
            else
            {
                rightClickExistingAmount.add(0);
            }

            /* - Distribute one item to the slot */
            lastRMBSlotId = slot.id;
            this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 1, false, this.minecraft.player);
        }
    }

    /**
     * @author telvarost
     */
    @Unique private boolean broom_cancelRightClickDrag(boolean isClientOnServer)
    {
        /* - Cancel Right-click + Drag */
        if (isRightClickDragStarted) {
            if (rightClickHoveredSlots.size() > 1) {
                /* - Slots cannot return to normal on a server */
                if (!isClientOnServer) {
                    /* - Return all slots to normal */
                    minecraft.player.inventory.setCursorStack(new ItemStack(rightClickPersistentStack.itemId, rightClickItemAmount, rightClickPersistentStack.getDamage()));
                    for (int leftClickHoveredSlotsIndex = 0; leftClickHoveredSlotsIndex < rightClickHoveredSlots.size(); leftClickHoveredSlotsIndex++) {
                        if (0 != rightClickExistingAmount.get(leftClickHoveredSlotsIndex)) {
                            rightClickHoveredSlots.get(leftClickHoveredSlotsIndex).setStack(new ItemStack(rightClickPersistentStack.itemId, rightClickExistingAmount.get(leftClickHoveredSlotsIndex), rightClickPersistentStack.getDamage()));
                        } else {
                            rightClickHoveredSlots.get(leftClickHoveredSlotsIndex).setStack(null);
                        }
                    }
                }

                /* - Reset Right-click + Drag variables and exit function */
                broom_resetRightClickDragVariables();

                return true;
            }
        }

        return false;
    }

    /**
     * @author telvarost
     */
    @Unique private void broom_resetRightClickDragVariables()
    {
        rightClickExistingAmount.clear();
        rightClickHoveredSlots.clear();
        rightClickPersistentStack = null;
        rightClickItemAmount = 0;
        isRightClickDragStarted = false;
    }

    /**
     * @author telvarost
     */
    @Unique private boolean broom_handleLeftClickWithItem(ItemStack cursorStack, Slot clickedSlot, boolean isClientOnServer) {
        /* - Ensure a slot was clicked */
        if (null != clickedSlot) {
            /* - Record how many items are in the slot and how many items are needed to fill the slot */
            if (null != clickedSlot.getStack()) {
                if (null != cursorStack) {
                    /* - Let vanilla minecraft handle left click with an item onto any item */
                    if (isClientOnServer) {
                        return false;
                    }

                    /* - Let vanilla minecraft handle left click with an item onto a different item */
                    if (!cursorStack.isItemEqual(clickedSlot.getStack())) {
                        return false;
                    }
                }

                leftClickAmountToFillPersistent.add(cursorStack.getMaxCount() - clickedSlot.getStack().count);
                leftClickExistingAmount.add(clickedSlot.getStack().count);
            } else {
                leftClickAmountToFillPersistent.add(cursorStack.getMaxCount());
                leftClickExistingAmount.add(0);
            }

            /* - Begin Left-click + Drag */
            if (cursorStack != null && leftClickPersistentStack == null && !isLeftClickDragStarted) {
                leftClickPersistentStack = cursorStack;
                leftClickItemAmount = leftClickPersistentStack.count;
                isLeftClickDragStarted = true;
            }

            /* - Handle initial Left-click */
            lastLMBSlotId = clickedSlot.id;
            lastLMBSlot = clickedSlot;
            boolean isShiftKeyDown = (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT));
            this.minecraft.interactionManager.clickSlot(this.container.syncId, clickedSlot.id, 0, isShiftKeyDown, this.minecraft.player);

            if (isShiftKeyDown) {
                broom_resetLeftClickDragVariables();
                leftClickMouseTweaksPersistentStack = cursorStack;
                isLeftClickDragMouseTweaksStarted = true;
            }

            return true;
        }

        return false;
    }

    /**
     * @author telvarost
     */
    @Unique private boolean broom_handleLeftClickWithoutItem(Slot clickedSlot) {
        isLeftClickDragMouseTweaksStarted = true;

        /* - Ensure a slot was clicked */
        if (clickedSlot != null) {
            /* - Get info for MouseTweaks `Left-Click + Drag` mechanics */
            ItemStack itemInSlot = clickedSlot.getStack();
            leftClickMouseTweaksPersistentStack = itemInSlot;

            /* - Handle initial Left-click */
            lastLMBSlotId = clickedSlot.id;
            lastLMBSlot = clickedSlot;
            boolean isShiftKeyDown = (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT));
            this.minecraft.interactionManager.clickSlot(this.container.syncId, clickedSlot.id, 0, isShiftKeyDown, this.minecraft.player);

            return true;
        } else {
            /* - Get info for MouseTweaks `Left-Click + Drag` mechanics */
            leftClickMouseTweaksPersistentStack = null;
        }

        return false;
    }

    /**
     * @author telvarost
     */
    @Unique private void broom_handleLeftClickDragMouseTweaks() {
        if (slot.id != lastLMBSlotId) {
            lastLMBSlotId = slot.id;

            ItemStack slotItemToExamine = slot.getStack();
            if (null != slotItemToExamine)
            {
                if (null != leftClickMouseTweaksPersistentStack)
                {
                    if (slotItemToExamine.isItemEqual(leftClickMouseTweaksPersistentStack))
                    {
                        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                            this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, true, this.minecraft.player);
                        } else {
                            ItemStack cursorStack = minecraft.player.inventory.getCursorStack();

                            if (cursorStack == null) {
                                /* - Pick up items from slot */
                                this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, false, this.minecraft.player);
                            } else if (cursorStack.count < leftClickMouseTweaksPersistentStack.getMaxCount()) {
                                int amountAbleToPickUp = leftClickMouseTweaksPersistentStack.getMaxCount() - cursorStack.count;
                                int amountInSlot = slotItemToExamine.count;

                                /* - Pick up items from slot */
                                if (amountInSlot <= amountAbleToPickUp) {
                                    this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, false, this.minecraft.player);
                                    this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, false, this.minecraft.player);
                                } else if (cursorStack.count == leftClickMouseTweaksPersistentStack.getMaxCount()) {
                                    slot.setStack(new ItemStack(leftClickMouseTweaksPersistentStack.itemId, cursorStack.count, leftClickMouseTweaksPersistentStack.getDamage()));
                                    minecraft.player.inventory.setCursorStack(new ItemStack(leftClickMouseTweaksPersistentStack.itemId, amountInSlot, leftClickMouseTweaksPersistentStack.getDamage()));
                                } else {
                                    this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, false, this.minecraft.player);

                                    slotItemToExamine = slot.getStack();
                                    cursorStack = minecraft.player.inventory.getCursorStack();
                                    amountInSlot = slotItemToExamine.count;

                                    slot.setStack(new ItemStack(leftClickMouseTweaksPersistentStack.itemId, cursorStack.count, leftClickMouseTweaksPersistentStack.getDamage()));
                                    minecraft.player.inventory.setCursorStack(new ItemStack(leftClickMouseTweaksPersistentStack.itemId, amountInSlot, leftClickMouseTweaksPersistentStack.getDamage()));
                                }
                            }
                        }
                    }
                } else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                    this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, true, this.minecraft.player);
                }
            }
        }
    }

    /**
     * @author telvarost
     */
    @Unique private boolean broom_handleLeftClickDrag()
    {
        /* - Do nothing if slot has already been added to Left-click + Drag logic */
        if (!leftClickHoveredSlots.contains(slot)) {
            ItemStack slotItemToExamine = slot.getStack();

            /* - Check if client is on a server */
            boolean isClientOnServer = minecraft.world.isRemote;

            /* - Do nothing if slot item does not match held item */
            if (null != slotItemToExamine){
                if (isClientOnServer) {
                    return true;
                }

                if (!slotItemToExamine.isItemEqual(leftClickPersistentStack)) {
                    return true;
                }
            }

            /* - Do nothing if there are no more items to distribute */
            if (1.0 == (double)leftClickItemAmount / (double)leftClickHoveredSlots.size()) {
                return true;
            }

            /* - First slot is handled instantly in mouseClicked function */
            if (slot.id != lastLMBSlotId) {
                if (leftClickHoveredSlots.isEmpty())
                {
                    /* - Add slot to item distribution */
                    leftClickHoveredSlots.add(lastLMBSlot);
                }

                /* - Add slot to item distribution */
                leftClickHoveredSlots.add(slot);

                /* - Record how many items are in the slot and how many items are needed to fill the slot */
                if (null != slotItemToExamine) {
                    leftClickAmountToFillPersistent.add(leftClickPersistentStack.getMaxCount() - slotItemToExamine.count);
                    leftClickExistingAmount.add(slotItemToExamine.count);
                }
                else
                {
                    leftClickAmountToFillPersistent.add(leftClickPersistentStack.getMaxCount());
                    leftClickExistingAmount.add(0);
                }

                /* - Slots cannot return to normal on a server */
                List<Integer> leftClickAmountToFill = new ArrayList<>();
                if (!isClientOnServer) {
                    /* - Return all slots to normal */
                    minecraft.player.inventory.setCursorStack(new ItemStack(leftClickPersistentStack.itemId, leftClickItemAmount, leftClickPersistentStack.getDamage()));
                    for (int leftClickHoveredSlotsIndex = 0; leftClickHoveredSlotsIndex < leftClickHoveredSlots.size(); leftClickHoveredSlotsIndex++) {
                        leftClickAmountToFill.add(leftClickAmountToFillPersistent.get(leftClickHoveredSlotsIndex));
                        if (0 != leftClickExistingAmount.get(leftClickHoveredSlotsIndex)) {
                            leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).setStack(new ItemStack(leftClickPersistentStack.itemId, leftClickExistingAmount.get(leftClickHoveredSlotsIndex), leftClickPersistentStack.getDamage()));
                        } else {
                            leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).setStack(null);
                        }
                    }
                }

                /* - Prepare to distribute over slots */
                int numberOfSlotsRemainingToFill = leftClickHoveredSlots.size();
                int itemsPerSlot = leftClickItemAmount / numberOfSlotsRemainingToFill;
                int leftClickRemainingItemAmount = leftClickItemAmount;
                boolean rerunLoop;

                /* - Slots cannot return to normal on a server */
                if (!isClientOnServer) {
                    /* - Distribute fewer items to slots whose max stack size will be filled */
                    do {
                        rerunLoop = false;
                        if (numberOfSlotsRemainingToFill > 0) {
                            itemsPerSlot = leftClickRemainingItemAmount / numberOfSlotsRemainingToFill;

                            if (0 != itemsPerSlot) {
                                for (int slotsToCheckIndex = 0; slotsToCheckIndex < leftClickAmountToFill.size(); slotsToCheckIndex++) {
                                    if (0 != leftClickAmountToFill.get(slotsToCheckIndex) && leftClickAmountToFill.get(slotsToCheckIndex) < itemsPerSlot) {
                                        /* - Just fill the slot and return */
                                        for (int fillTheAmountIndex = 0; fillTheAmountIndex < leftClickAmountToFill.get(slotsToCheckIndex); fillTheAmountIndex++) {
                                            this.minecraft.interactionManager.clickSlot(this.container.syncId, leftClickHoveredSlots.get(slotsToCheckIndex).id, 1, false, this.minecraft.player);
                                        }

                                        leftClickRemainingItemAmount = leftClickRemainingItemAmount - leftClickAmountToFill.get(slotsToCheckIndex);
                                        leftClickAmountToFill.set(slotsToCheckIndex, 0);
                                        numberOfSlotsRemainingToFill--;
                                        rerunLoop = true;
                                    }
                                }
                            }
                        }
                    } while (rerunLoop && 0 < numberOfSlotsRemainingToFill);
                } else {
                    /* - Return slots to normal on when client is on a server */
                    for (int leftClickHoveredSlotsIndex = 0; leftClickHoveredSlotsIndex < (leftClickHoveredSlots.size() - 1); leftClickHoveredSlotsIndex++)
                    {
                        ItemStack cursorStack = minecraft.player.inventory.getCursorStack();
                        if (leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).hasStack() && leftClickHoveredSlots.size() > 1)
                        {
                            if (cursorStack != null)
                            {
                                this.minecraft.interactionManager.clickSlot(this.container.syncId, leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).id, 0, false, this.minecraft.player);
                            }
                            this.minecraft.interactionManager.clickSlot(this.container.syncId, leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).id, 0, false, this.minecraft.player);
                        }
                    }
                }

                /* - Distribute remaining items evenly over remaining slots that were not already filled to max stack size */
                for (int distributeSlotsIndex = 0; distributeSlotsIndex < leftClickHoveredSlots.size(); distributeSlotsIndex++) {
                    if (isClientOnServer) {
                        if (0 != leftClickAmountToFillPersistent.get(distributeSlotsIndex)) {
                            for (int addSlotIndex = 0; addSlotIndex < itemsPerSlot; addSlotIndex++) {
                                this.minecraft.interactionManager.clickSlot(this.container.syncId, leftClickHoveredSlots.get(distributeSlotsIndex).id, 1, false, this.minecraft.player);
                            }
                        }
                    } else {
                        if (0 != leftClickAmountToFill.get(distributeSlotsIndex)) {
                            for (int addSlotIndex = 0; addSlotIndex < itemsPerSlot; addSlotIndex++) {
                                this.minecraft.interactionManager.clickSlot(this.container.syncId, leftClickHoveredSlots.get(distributeSlotsIndex).id, 1, false, this.minecraft.player);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * @author telvarost
     */
    @Unique private boolean broom_cancelLeftClickDrag(boolean isClientOnServer)
    {
        /* - Cancel Left-click + Drag */
        if (isLeftClickDragStarted) {
            if (leftClickHoveredSlots.size() > 1) {
                /* - Check if client is running on a server or not */
                if (!isClientOnServer) {
                    /* - Return all slots to normal */
                    minecraft.player.inventory.setCursorStack(new ItemStack(leftClickPersistentStack.itemId, leftClickItemAmount, leftClickPersistentStack.getDamage()));
                    for (int leftClickHoveredSlotsIndex = 0; leftClickHoveredSlotsIndex < leftClickHoveredSlots.size(); leftClickHoveredSlotsIndex++) {
                        if (0 != leftClickExistingAmount.get(leftClickHoveredSlotsIndex)) {
                            leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).setStack(new ItemStack(leftClickPersistentStack.itemId, leftClickExistingAmount.get(leftClickHoveredSlotsIndex), leftClickPersistentStack.getDamage()));
                        } else {
                            leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).setStack(null);
                        }
                    }
                } else {
                    /* - Return slots to normal on when client is on a server */
                    for (int leftClickHoveredSlotsIndex = 0; leftClickHoveredSlotsIndex < (leftClickHoveredSlots.size() - 1); leftClickHoveredSlotsIndex++)
                    {
                        ItemStack cursorStack = minecraft.player.inventory.getCursorStack();
                        if (leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).hasStack() && leftClickHoveredSlots.size() > 1)
                        {
                            if (cursorStack != null)
                            {
                                this.minecraft.interactionManager.clickSlot(this.container.syncId, leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).id, 0, false, this.minecraft.player);
                            }
                            this.minecraft.interactionManager.clickSlot(this.container.syncId, leftClickHoveredSlots.get(leftClickHoveredSlotsIndex).id, 0, false, this.minecraft.player);
                        }
                    }
                    this.minecraft.interactionManager.clickSlot(this.container.syncId, leftClickHoveredSlots.get((leftClickHoveredSlots.size() - 1)).id, 0, false, this.minecraft.player);
                    this.minecraft.interactionManager.clickSlot(this.container.syncId, leftClickHoveredSlots.get((leftClickHoveredSlots.size() - 1)).id, 0, false, this.minecraft.player);
                }

                /* - Reset Left-click + Drag variables and exit function */
                broom_resetLeftClickDragVariables();
                return true;
            }
        }

        return false;
    }

    /**
     * @author telvarost
     */
    @Unique private void broom_resetLeftClickDragVariables()
    {
        leftClickExistingAmount.clear();
        leftClickAmountToFillPersistent.clear();
        leftClickHoveredSlots.clear();
        leftClickPersistentStack = null;
        leftClickMouseTweaksPersistentStack = null;
        leftClickItemAmount = 0;
        isLeftClickDragStarted = false;
        isLeftClickDragMouseTweaksStarted = false;
    }

    /**
     * @author telvarost
     */
    @Unique
    private boolean drawingHoveredSlot;

    /**
     * @author telvarost
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;isPointOverSlot(Lnet/minecraft/screen/slot/Slot;II)Z"))
    private boolean broom_isMouseOverSlot(HandledScreen guiContainer, Slot slot, int x, int y) {
        return (  (drawingHoveredSlot = rightClickHoveredSlots.contains(slot))
                || (drawingHoveredSlot = leftClickHoveredSlots.contains(slot))
                || isPointOverSlot(slot, x, y)
        );
    }

    /**
     * @author telvarost
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;fillGradient(IIIIII)V", ordinal = 0))
    private void broom_fillGradient(HandledScreen instance, int startX, int startY, int endX, int endY, int colorStart, int colorEnd) {
        if (colorStart != colorEnd) throw new AssertionError();
        int color = drawingHoveredSlot ? 0x20ffffff : colorStart;
        this.fillGradient(startX, startY, endX, endY, color, color);
    }

    /**
     * @author telvarost
     */
    @Inject(method = "keyPressed", at = @At("RETURN"))
    private void broom_keyPressed(char character, int keyCode, CallbackInfo ci) {
        if (this.slot == null) {
            return;
        }

        if (keyCode == this.minecraft.options.dropKey.code) {
            if (this.minecraft.player.inventory.getCursorStack() != null) {
                return;
            }

            this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, false, this.minecraft.player);
            this.minecraft.interactionManager.clickSlot(this.container.syncId, -999, Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ? 0 : 1, false, this.minecraft.player);
            this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, false, this.minecraft.player);
        }

        if (keyCode >= Keyboard.KEY_1 && keyCode <= Keyboard.KEY_9) {
            if (this.minecraft.player.inventory.getCursorStack() == null) {
                this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, false, this.minecraft.player);
            }
            this.minecraft.interactionManager.clickSlot(this.container.syncId, 35 + keyCode - 1, 0, false, this.minecraft.player);
            this.minecraft.interactionManager.clickSlot(this.container.syncId, slot.id, 0, false, this.minecraft.player);
        }
    }
}
