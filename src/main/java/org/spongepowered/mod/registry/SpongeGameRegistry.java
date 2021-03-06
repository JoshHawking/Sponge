/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.spongepowered.mod.registry;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.GameData;

import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.effect.Particle;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.hanging.art.Art;
import org.spongepowered.api.entity.living.meta.DyeColor;
import org.spongepowered.api.entity.living.meta.HorseColor;
import org.spongepowered.api.entity.living.meta.HorseColors;
import org.spongepowered.api.entity.living.meta.HorseStyle;
import org.spongepowered.api.entity.living.meta.HorseStyles;
import org.spongepowered.api.entity.living.meta.HorseVariant;
import org.spongepowered.api.entity.living.meta.HorseVariants;
import org.spongepowered.api.entity.living.meta.OcelotType;
import org.spongepowered.api.entity.living.meta.OcelotTypes;
import org.spongepowered.api.entity.living.meta.RabbitType;
import org.spongepowered.api.entity.living.meta.RabbitTypes;
import org.spongepowered.api.entity.living.meta.SkeletonType;
import org.spongepowered.api.entity.living.meta.SkeletonTypes;
import org.spongepowered.api.entity.living.villager.Career;
import org.spongepowered.api.entity.living.villager.Profession;
import org.spongepowered.api.entity.player.gamemode.GameMode;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.item.merchant.TradeOfferBuilder;
import org.spongepowered.api.potion.PotionEffectBuilder;
import org.spongepowered.api.potion.PotionEffectType;
import org.spongepowered.api.potion.PotionEffectTypes;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.message.Messages;
import org.spongepowered.api.text.message.SpongeMessageFactory;
import org.spongepowered.api.text.title.SpongeTitleFactory;
import org.spongepowered.api.text.title.Titles;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.api.world.Environment;
import org.spongepowered.api.world.biome.BiomeType;
import org.spongepowered.api.world.biome.BiomeTypes;
import org.spongepowered.api.world.gamerule.DefaultGameRules;
import org.spongepowered.mod.entity.SpongeEntityConstants;
import org.spongepowered.mod.entity.SpongeEntityType;
import org.spongepowered.mod.text.chat.SpongeChatType;
import org.spongepowered.mod.text.format.SpongeTextColor;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

@SuppressWarnings("unchecked")
@NonnullByDefault
public class SpongeGameRegistry implements GameRegistry {

    private Map<String, BiomeType> biomeTypeMappings = Maps.newHashMap();
    public static Map<String, SpongeTextColor> textColorMappings = Maps.newHashMap();
    public static Map<TextColor, EnumChatFormatting> textColorToEnumMappings = Maps.newHashMap();
    public static final ImmutableMap<String, TextStyle.Base> textStyleMappings = new ImmutableMap.Builder<String, TextStyle.Base>()
                                                                                .put("OBFUSCATED", new TextStyle.Base("OBFUSCATED", 'k'))
                                                                                .put("BOLD", new TextStyle.Base("BOLD", 'l'))
                                                                                .put("STRIKETHROUGH", new TextStyle.Base("STRIKETHROUGH", 'm'))
                                                                                .put("UNDERLINE", new TextStyle.Base("UNDERLINE", 'n'))
                                                                                .put("ITALIC", new TextStyle.Base("ITALIC", 'o'))
                                                                                .put("RESET", new TextStyle.Base("RESET", 'r'))
                                                                                .build();
    private static final ImmutableMap<String, ChatType> chatTypeMappings = new ImmutableMap.Builder<String, ChatType>()
                                                                           .put("CHAT", new SpongeChatType("CHAT", (byte) 0))
                                                                           .put("SYSTEM", new SpongeChatType("SYSTEM", (byte) 1))
                                                                           .put("ACTION_BAR", new SpongeChatType("ACTION_BAR", (byte) 2))
                                                                           .build();

    private Map<String, SpongeEntityType> entityTypeMappings = Maps.newHashMap();
    public Map<String, SpongeEntityType> entityIdToTypeMappings = Maps.newHashMap();
    public Map<Class<? extends Entity>, SpongeEntityType> entityClassToTypeMappings = Maps.newHashMap();
    public Map<String, Enchantment> enchantmentMappings = Maps.newHashMap();

    @Override
    public Optional<BlockType> getBlock(String id) {
        return Optional.fromNullable((BlockType) GameData.getBlockRegistry().getObject(id));
    }

    @Override
    public Optional<ItemType> getItem(String id) {
        return Optional.fromNullable((ItemType) GameData.getItemRegistry().getObject(id));
    }

    public Optional<PotionEffectType> getPotion(String id) {
        return Optional.fromNullable((PotionEffectType)Potion.getPotionFromResourceLocation(id));
    }

    @Override
    public ItemStackBuilder getItemBuilder() {

        //TODO implement.
        return null;
    }

    @Override
    public TradeOfferBuilder getTradeOfferBuilder() {

        //TODO implement.
        return null;
    }

    @Override
    public List<PotionEffectType> getPotionEffects() {
        List<PotionEffectType> potionList = new ArrayList<PotionEffectType>();
        for (Potion potion : Potion.potionTypes) {
            if (potion != null) {
                PotionEffectType potionEffectType = (PotionEffectType)potion;
                potionList.add(potionEffectType);
            }
        }
        return potionList;
    }

    @Override
    public Optional<EntityType> getEntity(String id) {
        if (!id.contains(":")) {
            id = "minecraft:" + id;
        }
        return Optional.fromNullable((EntityType)this.entityIdToTypeMappings.get(id));
    }

    @Override
    public List<EntityType> getEntities() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<BiomeType> getBiome(String id) {
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome != null && biome.biomeName.equalsIgnoreCase(id)) {
                return Optional.of((BiomeType)biome);
            }
        }
        return Optional.absent();
    }

    @Override
    public List<BiomeType> getBiomes() {
        List<BiomeType> biomeTypes = new ArrayList<BiomeType>();
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome != null) {
                biomeTypes.add((BiomeType)biome);
            }
        }
        return biomeTypes;
    }

    @Override
    public List<BlockType> getBlocks() {
        Iterator<ResourceLocation> iter = GameData.getBlockRegistry().getKeys().iterator();
        List<BlockType> blockList = new ArrayList<BlockType>();
        while (iter.hasNext()) {
            blockList.add(getBlock(iter.next().toString()).get());
        }
        return blockList;
    }

    @Override
    public List<ItemType> getItems() {
        Iterator<ResourceLocation> iter = GameData.getItemRegistry().getKeys().iterator();
        List<ItemType> itemList = new ArrayList<ItemType>();
        while (iter.hasNext()) {
            itemList.add(getItem(iter.next().toString()).get());
        }
        return itemList;
    }

    @Override
    public Optional<Particle> getParticle(String id) {

        //TODO Implement getParticle once particles are implemented.
        return Optional.absent();
    }

    @Override
    public List<Particle> getParticles() {

        //TODO implement.
        return null;
    }

    @Override
    public List<String> getDefaultGameRules() {

        List<String> gameruleList = new ArrayList<String>();
        for(Field f : DefaultGameRules.class.getFields()) {
            try {
                gameruleList.add((String)f.get(null));
            } catch(Exception e) {
                //Ignoring error
            }
        }
        return gameruleList;
    }

    @Override
    public Optional<Art> getArt(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<Art> getArts() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<DyeColor> getDye(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<DyeColor> getDyes() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<HorseColor> getHorseColor(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<HorseColor> getHorseColors() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<HorseStyle> getHorseStyle(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<HorseStyle> getHorseStyles() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<HorseVariant> getHorseVariant(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<HorseVariant> getHorseVariants() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<OcelotType> getOcelotType(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<OcelotType> getOcelotTypes() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<RabbitType> getRabbitType(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<RabbitType> getRabbitTypes() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<SkeletonType> getSkeletonType(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<SkeletonType> getSkeletonTypes() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<Career> getCareer(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<Career> getCareers() {

        //TODO implement.
        return null;
    }

    @Override
    public List<Career> getCareers(Profession profession) {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<Profession> getProfession(String id) {

        //TODO implement.
        return Optional.absent();
    }

    @Override
    public List<Profession> getProfessions() {

        //TODO implement.
        return null;
    }

    @Override
    public List<GameMode> getGameModes() {

        //TODO implement.
        return null;
    }

    @Override
    public PotionEffectBuilder getPotionEffectBuilder() {

        //TODO implement.
        return null;
    }

    @Override
    public Optional<Enchantment> getEnchantment(String id) {
        return Optional.fromNullable((Enchantment) net.minecraft.enchantment.Enchantment.func_180305_b(id));
    }

    @Override
    public List<Enchantment> getEnchantments() {
        return new ArrayList<Enchantment>(this.enchantmentMappings.values());
    }

    @Override
    public Optional<Environment> getEnvironment(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Environment> getEnvironment(int dimensionId) {
        // TODO
        return null;
    }

    @Override
    public List<Environment> getEnvironments() {
        // TODO
        return null;
    }

    // Note: This is probably fairly slow, but only needs to be run rarely.
    private void setBlockTypes() {
        for (Field f : BlockTypes.class.getDeclaredFields()) {
            try {
                f.set(null, getBlock(f.getName().toLowerCase()).get());
            } catch (Exception e) {
                // Ignoring error
            }
        }
    }

    // Note: This is probably fairly slow, but only needs to be run rarely.
    private void setItemTypes() {
        for (Field f : ItemTypes.class.getDeclaredFields()) {
            try {
                f.set(null, getItem(f.getName().toLowerCase()).get());
            } catch (Exception e) {
                // Ignoring error
            }
        }
    }

    private void setEnchantments() {
        this.enchantmentMappings.put("PROTECTION", (Enchantment) net.minecraft.enchantment.Enchantment.field_180310_c);
        this.enchantmentMappings.put("FIRE_PROTECTION", (Enchantment) net.minecraft.enchantment.Enchantment.fireProtection);
        this.enchantmentMappings.put("FEATHER_FALLING", (Enchantment) net.minecraft.enchantment.Enchantment.field_180309_e);
        this.enchantmentMappings.put("BLAST_PROTECTION", (Enchantment) net.minecraft.enchantment.Enchantment.blastProtection);
        this.enchantmentMappings.put("PROJECTILE_PROTECTION", (Enchantment) net.minecraft.enchantment.Enchantment.field_180308_g);
        this.enchantmentMappings.put("RESPIRATION", (Enchantment) net.minecraft.enchantment.Enchantment.field_180317_h);
        this.enchantmentMappings.put("AQUA_AFFINITY", (Enchantment) net.minecraft.enchantment.Enchantment.aquaAffinity);
        this.enchantmentMappings.put("THORNS", (Enchantment) net.minecraft.enchantment.Enchantment.thorns);
        this.enchantmentMappings.put("DEPTH_STRIDER", (Enchantment) net.minecraft.enchantment.Enchantment.field_180316_k);
        this.enchantmentMappings.put("SHARPNESS", (Enchantment) net.minecraft.enchantment.Enchantment.field_180314_l);
        this.enchantmentMappings.put("SMITE", (Enchantment) net.minecraft.enchantment.Enchantment.field_180315_m);
        this.enchantmentMappings.put("BANE_OF_ARTHROPODS", (Enchantment) net.minecraft.enchantment.Enchantment.field_180312_n);
        this.enchantmentMappings.put("KNOCKBACK", (Enchantment) net.minecraft.enchantment.Enchantment.field_180313_o);
        this.enchantmentMappings.put("FIRE_ASPECT", (Enchantment) net.minecraft.enchantment.Enchantment.fireAspect);
        this.enchantmentMappings.put("LOOTING", (Enchantment) net.minecraft.enchantment.Enchantment.looting);
        this.enchantmentMappings.put("EFFICIENCY", (Enchantment) net.minecraft.enchantment.Enchantment.efficiency);
        this.enchantmentMappings.put("SILK_TOUCH", (Enchantment) net.minecraft.enchantment.Enchantment.silkTouch);
        this.enchantmentMappings.put("UNBREAKING", (Enchantment) net.minecraft.enchantment.Enchantment.unbreaking);
        this.enchantmentMappings.put("FORTUNE", (Enchantment) net.minecraft.enchantment.Enchantment.fortune);
        this.enchantmentMappings.put("POWER", (Enchantment) net.minecraft.enchantment.Enchantment.power);
        this.enchantmentMappings.put("PUNCH", (Enchantment) net.minecraft.enchantment.Enchantment.punch);
        this.enchantmentMappings.put("FLAME", (Enchantment) net.minecraft.enchantment.Enchantment.flame);
        this.enchantmentMappings.put("INFINITY", (Enchantment) net.minecraft.enchantment.Enchantment.infinity);
        this.enchantmentMappings.put("LUCK_OF_THE_SEA", (Enchantment) net.minecraft.enchantment.Enchantment.luckOfTheSea);
        this.enchantmentMappings.put("LURE", (Enchantment) net.minecraft.enchantment.Enchantment.lure);
        for (Field f : Enchantments.class.getDeclaredFields()) {
            try {
                f.set(null, this.enchantmentMappings.get(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // Note: This is probably fairly slow, but only needs to be run rarely.
    private void setPotionTypes() {
        for (Field f : PotionEffectTypes.class.getDeclaredFields()) {
            try {
                f.set(null, getPotion(f.getName().toLowerCase()).get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setEntityTypes() {
        // internal mapping of our EntityTypes to actual MC names
        this.entityTypeMappings.put("DROPPED_ITEM", new SpongeEntityType(1, "Item", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Item")));
        this.entityTypeMappings.put("EXPERIENCE_ORB", new SpongeEntityType(2, "XPOrb", (Class<? extends Entity>)EntityList.stringToClassMapping.get("XPOrb")));
        this.entityTypeMappings.put("LEASH_HITCH", new SpongeEntityType(8, "LeashKnot", (Class<? extends Entity>)EntityList.stringToClassMapping.get("LeashKnot")));
        this.entityTypeMappings.put("PAINTING", new SpongeEntityType(9, "Painting", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Painting")));
        this.entityTypeMappings.put("ARROW", new SpongeEntityType(10, "Arrow", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Arrow")));
        this.entityTypeMappings.put("SNOWBALL", new SpongeEntityType(11, "Snowball", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Snowball")));
        this.entityTypeMappings.put("FIREBALL", new SpongeEntityType(12, "LargeFireball", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Fireball")));
        this.entityTypeMappings.put("SMALL_FIREBALL", new SpongeEntityType(13, "SmallFireball", (Class<? extends Entity>)EntityList.stringToClassMapping.get("SmallFireball")));
        this.entityTypeMappings.put("ENDER_PEARL", new SpongeEntityType(14, "ThrownEnderpearl", (Class<? extends Entity>)EntityList.stringToClassMapping.get("ThrownEnderpearl")));
        this.entityTypeMappings.put("EYE_OF_ENDER", new SpongeEntityType(15, "EyeOfEnderSignal", (Class<? extends Entity>)EntityList.stringToClassMapping.get("EyeOfEnderSignal")));
        this.entityTypeMappings.put("SPLASH_POTION", new SpongeEntityType(16, "ThrownPotion", (Class<? extends Entity>)EntityList.stringToClassMapping.get("ThrownPotion")));
        this.entityTypeMappings.put("THROWN_EXP_BOTTLE", new SpongeEntityType(17, "ThrownExpBottle", (Class<? extends Entity>)EntityList.stringToClassMapping.get("ThrownExpBottle")));
        this.entityTypeMappings.put("ITEM_FRAME", new SpongeEntityType(18, "ItemFrame", (Class<? extends Entity>)EntityList.stringToClassMapping.get("ItemFrame")));
        this.entityTypeMappings.put("WITHER_SKULL", new SpongeEntityType(19, "WitherSkull", (Class<? extends Entity>)EntityList.stringToClassMapping.get("WitherSkull")));
        this.entityTypeMappings.put("PRIMED_TNT", new SpongeEntityType(20, "PrimedTnt", (Class<? extends Entity>)EntityList.stringToClassMapping.get("PrimedTnt")));
        this.entityTypeMappings.put("FALLING_BLOCK", new SpongeEntityType(21, "FallingSand", (Class<? extends Entity>)EntityList.stringToClassMapping.get("FallingSand")));
        this.entityTypeMappings.put("FIREWORK", new SpongeEntityType(22, "FireworksRocketEntity", (Class<? extends Entity>)EntityList.stringToClassMapping.get("FireworksRocketEntity")));
        this.entityTypeMappings.put("ARMORSTAND", new SpongeEntityType(30, "ArmorStand", (Class<? extends Entity>)EntityList.stringToClassMapping.get("ArmorStand")));;
        this.entityTypeMappings.put("BOAT", new SpongeEntityType(41, "Boat", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Boat")));
        this.entityTypeMappings.put("RIDEABLE_MINECART", new SpongeEntityType(42, "MinecartRideable", (Class<? extends Entity>)EntityList.stringToClassMapping.get("MinecartRideable")));
        this.entityTypeMappings.put("CHESTED_MINECART", new SpongeEntityType(43, "MinecartChest", (Class<? extends Entity>)EntityList.stringToClassMapping.get("MinecartChest")));
        this.entityTypeMappings.put("FURNACE_MINECART", new SpongeEntityType(44, "MinecartFurnace", (Class<? extends Entity>)EntityList.stringToClassMapping.get("MinecartFurnace")));
        this.entityTypeMappings.put("TNT_MINECART", new SpongeEntityType(45, "MinecartTnt", (Class<? extends Entity>)EntityList.stringToClassMapping.get("MinecartTNT")));
        this.entityTypeMappings.put("HOPPER_MINECART", new SpongeEntityType(46, "MinecartHopper", (Class<? extends Entity>)EntityList.stringToClassMapping.get("MinecartHopper")));
        this.entityTypeMappings.put("MOB_SPAWNER_MINECART", new SpongeEntityType(47, "MinecartSpawner", (Class<? extends Entity>)EntityList.stringToClassMapping.get("MinecartSpawner")));
        this.entityTypeMappings.put("COMMANDBLOCK_MINECART", new SpongeEntityType(40, "MinecartCommandBlock", (Class<? extends Entity>)EntityList.stringToClassMapping.get("MinecartCommandBlock")));
        this.entityTypeMappings.put("CREEPER", new SpongeEntityType(50, "Creeper", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Creeper")));
        this.entityTypeMappings.put("SKELETON", new SpongeEntityType(51, "Skeleton", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Skeleton")));
        this.entityTypeMappings.put("SPIDER", new SpongeEntityType(52, "Spider", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Spider")));
        this.entityTypeMappings.put("GIANT", new SpongeEntityType(53, "Giant", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Giant")));
        this.entityTypeMappings.put("ZOMBIE", new SpongeEntityType(54, "Zombie", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Zombie")));
        this.entityTypeMappings.put("SLIME", new SpongeEntityType(55, "Slime", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Slime")));
        this.entityTypeMappings.put("GHAST", new SpongeEntityType(56, "Ghast", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Ghast")));
        this.entityTypeMappings.put("PIG_ZOMBIE", new SpongeEntityType(57, "PigZombie", (Class<? extends Entity>)EntityList.stringToClassMapping.get("PigZombie")));
        this.entityTypeMappings.put("ENDERMAN", new SpongeEntityType(58, "Enderman", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Enderman")));
        this.entityTypeMappings.put("CAVE_SPIDER", new SpongeEntityType(59, "CaveSpider", (Class<? extends Entity>)EntityList.stringToClassMapping.get("CaveSpider")));
        this.entityTypeMappings.put("SILVERFISH", new SpongeEntityType(60, "Silverfish", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Silverfish")));
        this.entityTypeMappings.put("BLAZE", new SpongeEntityType(61, "Blaze", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Blaze")));
        this.entityTypeMappings.put("MAGMA_CUBE", new SpongeEntityType(62, "LavaSlime", (Class<? extends Entity>)EntityList.stringToClassMapping.get("LavaSlime")));
        this.entityTypeMappings.put("ENDER_DRAGON", new SpongeEntityType(63, "EnderDragon", (Class<? extends Entity>)EntityList.stringToClassMapping.get("EnderDragon")));
        this.entityTypeMappings.put("WITHER", new SpongeEntityType(64, "WitherBoss", (Class<? extends Entity>)EntityList.stringToClassMapping.get("WitherBoss")));
        this.entityTypeMappings.put("BAT", new SpongeEntityType(65, "Bat", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Bat")));
        this.entityTypeMappings.put("WITCH", new SpongeEntityType(66, "Witch", (Class<? extends Entity>)EntityList.stringToClassMapping.get("FallingSand")));
        this.entityTypeMappings.put("ENDERMITE", new SpongeEntityType(67, "Endermite", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Endermite")));
        this.entityTypeMappings.put("GUARDIAN", new SpongeEntityType(68, "Guardian", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Guardian")));
        this.entityTypeMappings.put("PIG", new SpongeEntityType(90, "Pig", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Pig")));
        this.entityTypeMappings.put("SHEEP", new SpongeEntityType(91, "Sheep", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Sheep")));
        this.entityTypeMappings.put("COW", new SpongeEntityType(92, "Cow", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Cow")));
        this.entityTypeMappings.put("CHICKEN", new SpongeEntityType(93, "Chicken", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Chicken")));
        this.entityTypeMappings.put("SQUID", new SpongeEntityType(94, "Squid", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Squid")));
        this.entityTypeMappings.put("WOLF", new SpongeEntityType(95, "Wolf", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Wolf")));
        this.entityTypeMappings.put("MUSHROOM_COW", new SpongeEntityType(96, "MushroomCow", (Class<? extends Entity>)EntityList.stringToClassMapping.get("MushroomCow")));
        this.entityTypeMappings.put("SNOWMAN", new SpongeEntityType(97, "SnowMan", (Class<? extends Entity>)EntityList.stringToClassMapping.get("SnowMan")));
        this.entityTypeMappings.put("OCELOT", new SpongeEntityType(98, "Ozelot", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Ozelot")));
        this.entityTypeMappings.put("IRON_GOLEM", new SpongeEntityType(99, "VillagerGolem", (Class<? extends Entity>)EntityList.stringToClassMapping.get("VillagerGolem")));
        this.entityTypeMappings.put("HORSE", new SpongeEntityType(100, "EntityHorse", (Class<? extends Entity>)EntityList.stringToClassMapping.get("EntityHorse")));
        this.entityTypeMappings.put("RABBIT", new SpongeEntityType(101, "Rabbit", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Rabbit")));
        this.entityTypeMappings.put("VILLAGER", new SpongeEntityType(120, "Villager", (Class<? extends Entity>)EntityList.stringToClassMapping.get("Villager")));
        this.entityTypeMappings.put("ENDER_CRYSTAL", new SpongeEntityType(200, "EnderCrystal", (Class<? extends Entity>)EntityList.stringToClassMapping.get("EnderCrystal")));
        this.entityTypeMappings.put("EGG", new SpongeEntityType(-1, "Egg", EntityEgg.class));
        this.entityTypeMappings.put("FISHING_HOOK", new SpongeEntityType(-2, "FishingHook", EntityFishHook.class));
        this.entityTypeMappings.put("LIGHTNING", new SpongeEntityType(-3, "Lightning", EntityLightningBolt.class));
        this.entityTypeMappings.put("WEATHER", new SpongeEntityType(-4, "Weather", EntityWeatherEffect.class));
        this.entityTypeMappings.put("PLAYER", new SpongeEntityType(-5, "Player", EntityPlayerMP.class));
        this.entityTypeMappings.put("COMPLEX_PART", new SpongeEntityType(-6, "ComplexPart", EntityDragonPart.class));

        for (Field f : EntityTypes.class.getDeclaredFields()) {
            try {
                EntityType entityType = this.entityTypeMappings.get(f.getName());
                f.set(null, this.entityTypeMappings.get(f.getName()));
                this.entityClassToTypeMappings.put(((SpongeEntityType)entityType).entityClass, (SpongeEntityType)entityType);
                this.entityIdToTypeMappings.put(((SpongeEntityType)entityType).modId, ((SpongeEntityType)entityType));
            } catch (Exception e) {
                // Ignore errors
            }
        }

        // skeleton types
        for (Field f : SkeletonTypes.class.getDeclaredFields()) {
            try {
                f.set(null, SpongeEntityConstants.SKELETON_TYPES.get(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // horse colors
        for (Field f : HorseColors.class.getDeclaredFields()) {
            try {
                f.set(null, SpongeEntityConstants.HORSE_COLORS.get(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // horse variants
        for (Field f : HorseVariants.class.getDeclaredFields()) {
            try {
                f.set(null, SpongeEntityConstants.HORSE_VARIANTS.get(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Field f : HorseStyles.class.getDeclaredFields()) {
            try {
                f.set(null, SpongeEntityConstants.HORSE_STYLES.get(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Field f : OcelotTypes.class.getDeclaredFields()) {
            try {
                f.set(null, SpongeEntityConstants.OCELOT_TYPES.get(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Field f : RabbitTypes.class.getDeclaredFields()) {
            try {
                f.set(null, SpongeEntityConstants.RABBIT_TYPES.get(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setBiomeTypes() {
        this.biomeTypeMappings.put("OCEAN", (BiomeType)BiomeGenBase.ocean);
        this.biomeTypeMappings.put("PLAINS", (BiomeType)BiomeGenBase.plains);
        this.biomeTypeMappings.put("DESERT", (BiomeType)BiomeGenBase.desert);
        this.biomeTypeMappings.put("EXTREME_HILLS", (BiomeType)BiomeGenBase.extremeHills);
        this.biomeTypeMappings.put("FOREST", (BiomeType)BiomeGenBase.forest);
        this.biomeTypeMappings.put("TAIGA", (BiomeType)BiomeGenBase.taiga);
        this.biomeTypeMappings.put("SWAMPLAND", (BiomeType)BiomeGenBase.swampland);
        this.biomeTypeMappings.put("RIVER", (BiomeType)BiomeGenBase.river);
        this.biomeTypeMappings.put("HELL", (BiomeType)BiomeGenBase.hell);
        this.biomeTypeMappings.put("SKY", (BiomeType)BiomeGenBase.sky);
        this.biomeTypeMappings.put("FROZEN_OCEAN", (BiomeType)BiomeGenBase.frozenOcean);
        this.biomeTypeMappings.put("FROZEN_RIVER", (BiomeType)BiomeGenBase.frozenRiver);
        this.biomeTypeMappings.put("ICE_PLAINS", (BiomeType)BiomeGenBase.icePlains);
        this.biomeTypeMappings.put("ICE_MOUNTAINS", (BiomeType)BiomeGenBase.iceMountains);
        this.biomeTypeMappings.put("MUSHROOM_ISLAND", (BiomeType)BiomeGenBase.mushroomIsland);
        this.biomeTypeMappings.put("MUSHROOM_ISLAND_SHORE", (BiomeType)BiomeGenBase.mushroomIslandShore);
        this.biomeTypeMappings.put("BEACH", (BiomeType)BiomeGenBase.beach);
        this.biomeTypeMappings.put("DESERT_HILLS", (BiomeType)BiomeGenBase.desertHills);
        this.biomeTypeMappings.put("FOREST_HILLS", (BiomeType)BiomeGenBase.forestHills);
        this.biomeTypeMappings.put("TAIGA_HILLS", (BiomeType)BiomeGenBase.taigaHills);
        this.biomeTypeMappings.put("EXTREME_HILLS_EDGE", (BiomeType)BiomeGenBase.extremeHillsEdge);
        this.biomeTypeMappings.put("JUNGLE", (BiomeType)BiomeGenBase.jungle);
        this.biomeTypeMappings.put("JUNGLE_HILLS", (BiomeType)BiomeGenBase.jungleHills);
        this.biomeTypeMappings.put("JUNGLE_EDGE", (BiomeType)BiomeGenBase.jungleEdge);
        this.biomeTypeMappings.put("DEEP_OCEAN", (BiomeType)BiomeGenBase.deepOcean);
        this.biomeTypeMappings.put("STONE_BEACH", (BiomeType)BiomeGenBase.stoneBeach);
        this.biomeTypeMappings.put("COLD_BEACH", (BiomeType)BiomeGenBase.coldBeach);
        this.biomeTypeMappings.put("BIRCH_FOREST", (BiomeType)BiomeGenBase.birchForest);
        this.biomeTypeMappings.put("BIRCH_FOREST_HILLS", (BiomeType)BiomeGenBase.birchForestHills);
        this.biomeTypeMappings.put("ROOFED_FOREST", (BiomeType)BiomeGenBase.roofedForest);
        this.biomeTypeMappings.put("COLD_TAIGA", (BiomeType)BiomeGenBase.coldTaiga);
        this.biomeTypeMappings.put("COLD_TAIGA_HILLS", (BiomeType)BiomeGenBase.coldTaigaHills);
        this.biomeTypeMappings.put("MEGA_TAIGA", (BiomeType)BiomeGenBase.megaTaiga);
        this.biomeTypeMappings.put("MEGA_TAIGA_HILLS", (BiomeType)BiomeGenBase.megaTaigaHills);
        this.biomeTypeMappings.put("EXTREME_HILLS_PLUS", (BiomeType)BiomeGenBase.extremeHillsPlus);
        this.biomeTypeMappings.put("SAVANNA", (BiomeType)BiomeGenBase.savanna);
        this.biomeTypeMappings.put("SAVANNA_PLATEAU", (BiomeType)BiomeGenBase.savannaPlateau);
        this.biomeTypeMappings.put("MESA", (BiomeType)BiomeGenBase.mesa);
        this.biomeTypeMappings.put("MESA_PLATEAU_FOREST", (BiomeType)BiomeGenBase.mesaPlateau_F);
        this.biomeTypeMappings.put("MESA_PLATEAU", (BiomeType)BiomeGenBase.mesaPlateau);
        this.biomeTypeMappings.put("SUNFLOWER_PLAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.plains.biomeID + 128]);
        this.biomeTypeMappings.put("DESERT_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.desert.biomeID + 128]);
        this.biomeTypeMappings.put("FLOWER_FOREST", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.forest.biomeID + 128]);
        this.biomeTypeMappings.put("TAIGA_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.taiga.biomeID + 128]);
        this.biomeTypeMappings.put("SWAMPLAND_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.swampland.biomeID + 128]);
        this.biomeTypeMappings.put("ICE_PLAINS_SPIKES", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.icePlains.biomeID + 128]);
        this.biomeTypeMappings.put("JUNGLE_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.jungle.biomeID + 128]);
        this.biomeTypeMappings.put("JUNGLE_EDGE_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.jungleEdge.biomeID + 128]);
        this.biomeTypeMappings.put("COLD_TAIGA_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.coldTaiga.biomeID + 128]);
        this.biomeTypeMappings.put("SAVANNA_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.savanna.biomeID + 128]);
        this.biomeTypeMappings.put("SAVANNA_PLATEAU_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.savannaPlateau.biomeID + 128]);
        this.biomeTypeMappings.put("MESA_BRYCE", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.mesa.biomeID + 128]);
        this.biomeTypeMappings.put("MESA_PLATEAU_FOREST_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.mesaPlateau_F.biomeID + 128]);
        this.biomeTypeMappings.put("MESA_PLATEAU_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.mesaPlateau.biomeID + 128]);
        this.biomeTypeMappings.put("BIRCH_FOREST_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.birchForest.biomeID + 128]);
        this.biomeTypeMappings.put("BIRCH_FOREST_HILLS_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.birchForestHills.biomeID + 128]);
        this.biomeTypeMappings.put("ROOFED_FOREST_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.roofedForest.biomeID + 128]);
        this.biomeTypeMappings.put("MEGA_SPRUCE_TAIGA", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.megaTaiga.biomeID + 128]);
        this.biomeTypeMappings.put("EXTREME_HILLS_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.extremeHills.biomeID + 128]);
        this.biomeTypeMappings.put("EXTREME_HILLS_PLUS_MOUNTAINS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.extremeHillsPlus.biomeID + 128]);
        this.biomeTypeMappings.put("MEGA_SPRUCE_TAIGA_HILLS", (BiomeType)BiomeGenBase.getBiomeGenArray()[BiomeGenBase.megaTaigaHills.biomeID + 128]);

        for (Field f : BiomeTypes.class.getDeclaredFields()) {
            try {
                f.set(null, this.biomeTypeMappings.get(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setTextColors() {
        textColorMappings.put("AQUA", new SpongeTextColor(Color.decode("0x00FFFF"), EnumChatFormatting.AQUA));
        textColorMappings.put("BLACK", new SpongeTextColor(Color.BLACK, EnumChatFormatting.BLACK));
        textColorMappings.put("BLUE", new SpongeTextColor(Color.decode("0x5555FF"), EnumChatFormatting.BLUE));
        textColorMappings.put("DARK_AQUA", new SpongeTextColor(Color.decode("0x00AAAA"), EnumChatFormatting.DARK_AQUA));
        textColorMappings.put("DARK_BLUE", new SpongeTextColor(Color.decode("0x0000AA"), EnumChatFormatting.DARK_BLUE));
        textColorMappings.put("DARK_GRAY", new SpongeTextColor(Color.decode("0x555555"), EnumChatFormatting.DARK_GRAY));
        textColorMappings.put("DARK_GREEN", new SpongeTextColor(Color.decode("0x00AA00"), EnumChatFormatting.DARK_GREEN));
        textColorMappings.put("DARK_PURPLE", new SpongeTextColor(Color.decode("0xAA00AA"), EnumChatFormatting.DARK_PURPLE));
        textColorMappings.put("DARK_RED", new SpongeTextColor(Color.decode("0xAA0000"), EnumChatFormatting.DARK_RED));
        textColorMappings.put("GOLD", new SpongeTextColor(Color.decode("0xFFAA00"), EnumChatFormatting.GOLD));
        textColorMappings.put("GRAY", new SpongeTextColor(Color.decode("0xAAAAAA"), EnumChatFormatting.GRAY));
        textColorMappings.put("GREEN", new SpongeTextColor(Color.decode("0x55FF55"), EnumChatFormatting.GREEN));
        textColorMappings.put("LIGHT_PURPLE", new SpongeTextColor(Color.decode("0xFF55FF"), EnumChatFormatting.LIGHT_PURPLE));
        textColorMappings.put("RED", new SpongeTextColor(Color.decode("0xFF5555"), EnumChatFormatting.RED));
        textColorMappings.put("RESET", new SpongeTextColor(Color.WHITE, EnumChatFormatting.RESET));
        textColorMappings.put("YELLOW", new SpongeTextColor(Color.decode("0xFFFF55"), EnumChatFormatting.YELLOW));

        textColorToEnumMappings.put(textColorMappings.get("AQUA"), EnumChatFormatting.AQUA);
        textColorToEnumMappings.put(textColorMappings.get("BLACK"), EnumChatFormatting.BLACK);
        textColorToEnumMappings.put(textColorMappings.get("BLUE"), EnumChatFormatting.BLUE);
        textColorToEnumMappings.put(textColorMappings.get("DARK_AQUA"), EnumChatFormatting.DARK_AQUA);
        textColorToEnumMappings.put(textColorMappings.get("DARK_BLUE"), EnumChatFormatting.DARK_BLUE);
        textColorToEnumMappings.put(textColorMappings.get("DARK_GRAY"), EnumChatFormatting.DARK_GRAY);
        textColorToEnumMappings.put(textColorMappings.get("DARK_GREEN"), EnumChatFormatting.DARK_GREEN);
        textColorToEnumMappings.put(textColorMappings.get("DARK_PURPLE"), EnumChatFormatting.DARK_PURPLE);
        textColorToEnumMappings.put(textColorMappings.get("DARK_RED"), EnumChatFormatting.DARK_RED);
        textColorToEnumMappings.put(textColorMappings.get("GOLD"), EnumChatFormatting.GOLD);
        textColorToEnumMappings.put(textColorMappings.get("GRAY"), EnumChatFormatting.GRAY);
        textColorToEnumMappings.put(textColorMappings.get("GREEN"), EnumChatFormatting.GREEN);
        textColorToEnumMappings.put(textColorMappings.get("LIGHT_PURPLE"), EnumChatFormatting.LIGHT_PURPLE);
        textColorToEnumMappings.put(textColorMappings.get("RED"), EnumChatFormatting.RED);
        textColorToEnumMappings.put(textColorMappings.get("RESET"), EnumChatFormatting.RESET);
        textColorToEnumMappings.put(textColorMappings.get("YELLOW"), EnumChatFormatting.YELLOW);

        for (Field f : TextColors.class.getDeclaredFields()) {
            try {
                f.set(null, textColorMappings.get(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Field f : TextStyles.class.getDeclaredFields()) {
            try {
                if (!f.getName().equals("NONE") && !f.getName().equals("ZERO")) { // ignore these as they are already implemented in API
                    f.set(null, textStyleMappings.get(f.getName()));
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        for (Field f : ChatTypes.class.getDeclaredFields()) {
            try {
                f.set(null, chatTypeMappings.get(f.getName()));
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }

    private void setMessageFactory() {
        try {
            Messages.class.getDeclaredField("factory").set(null, new SpongeMessageFactory());
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    private void setTitleFactory() {
        try {
            Titles.class.getDeclaredField("factory").set(null, new SpongeTitleFactory());
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }


    public void init() {
        setBiomeTypes();
        setBlockTypes();
        setItemTypes();
        setEnchantments();
        setPotionTypes();
        setEntityTypes();
        setTextColors();
        setMessageFactory();
        setTitleFactory();
    }

}
