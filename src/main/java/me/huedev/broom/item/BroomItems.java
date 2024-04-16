package me.huedev.broom.item;

import me.huedev.broom.Broom;
import me.huedev.broom.block.BroomBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.template.item.TemplateMusicDiscItem;
import net.modificationstation.stationapi.api.template.item.TemplateSeedsItem;

public class BroomItems {
    public static Item[] MUSIC_DISCS;

    public static Item RECORD_ELEVEN;
    public static Item RECORD_BLOCKS;
    public static Item RECORD_CHIRP;
    public static Item RECORD_FAR;
    public static Item RECORD_MALL;
    public static Item RECORD_MELLOHI;
    public static Item RECORD_STAL;
    public static Item RECORD_STRAD;
    public static Item RECORD_WARD;
    public static Item RECORD_WAIT;
    public static Item PUMPKIN_SEEDS;

    public static void init() {
        RECORD_ELEVEN = new TemplateMusicDiscItem(Broom.id("music_disc_11"), "11").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");
        RECORD_BLOCKS = new TemplateMusicDiscItem(Broom.id("music_disc_blocks"), "blocks").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");
        RECORD_CHIRP = new TemplateMusicDiscItem(Broom.id("music_disc_chirp"), "chirp").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");
        RECORD_FAR = new TemplateMusicDiscItem(Broom.id("music_disc_far"), "far").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");
        RECORD_MALL = new TemplateMusicDiscItem(Broom.id("music_disc_mall"), "blocks").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");
        RECORD_MELLOHI = new TemplateMusicDiscItem(Broom.id("music_disc_mellohi"), "mellohi").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");
        RECORD_STAL = new TemplateMusicDiscItem(Broom.id("music_disc_stal"), "stal").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");
        RECORD_STRAD = new TemplateMusicDiscItem(Broom.id("music_disc_strad"), "strad").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");
        RECORD_WARD = new TemplateMusicDiscItem(Broom.id("music_disc_ward"), "ward").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");
        RECORD_WAIT = new TemplateMusicDiscItem(Broom.id("music_disc_wait"), "wait").setMaxCount(1).setTranslationKey(Broom.NAMESPACE, "music_disc");

        PUMPKIN_SEEDS = new TemplateSeedsItem(Broom.id("pumpkin_seeds"), BroomBlocks.PUMPKIN_CROPS.id).setTranslationKey(Broom.NAMESPACE, "pumpkin_seeds");

        MUSIC_DISCS = new Item[] {
                Item.RECORD_THIRTEEN,
                Item.RECORD_CAT,
                RECORD_ELEVEN,
                RECORD_BLOCKS,
                RECORD_CHIRP,
                RECORD_FAR,
                RECORD_MALL,
                RECORD_MELLOHI,
                RECORD_STAL,
                RECORD_STRAD,
                RECORD_WARD,
                RECORD_WAIT
        };
    }
}
