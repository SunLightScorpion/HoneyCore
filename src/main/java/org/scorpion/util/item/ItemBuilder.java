package org.scorpion.util.item;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 * @author Lukas on 12/19/2021
 */
public class ItemBuilder implements Cloneable {

    ItemStack item;
    ItemMeta meta;

    public ItemBuilder(@NotNull Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(@NotNull ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ItemBuilder setDataContainer(NamespacedKey key) {
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setOwningSkull(String name) {
        SkullMeta skull = (SkullMeta) meta;
        skull.setOwningPlayer(Bukkit.getOfflinePlayer(name));
        return this;
    }

    public ItemBuilder setOwningSkull(UUID uuid) {
        SkullMeta skull = (SkullMeta) meta;
        skull.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        return this;
    }

    public ItemBuilder colorLeather(Color color) {
        LeatherArmorMeta m = (LeatherArmorMeta) meta;
        m.setColor(color);
        return this;
    }

    public ItemBuilder getItemFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder removeOneAmount() {
        item.setAmount(item.getAmount() - 1);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean breakable) {
        meta.setUnbreakable(breakable);
        return this;
    }

    public ItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder romanIndulgesRandom(int amount) {
        boolean give = new Random().nextBoolean();
        return give ? randomAmount(amount, false) : setAmount(0);
    }

    public ItemBuilder romanIndulges(int amount) {
        boolean give = new Random().nextBoolean();
        return give ? setAmount(amount) : setAmount(0);
    }

    public ItemBuilder romanIndulges(int amount, boolean random) {
        boolean give = new Random().nextBoolean();
        if (random) {
            var i = new Random().nextInt(amount) + 1;
            return give ? setAmount(i) : setAmount(0);
        }
        return give ? setAmount(amount) : setAmount(0);
    }

    public ItemBuilder romanIndulges() {
        boolean give = new Random().nextBoolean();
        return give ? setAmount(1) : setAmount(0);
    }

    public ItemBuilder randomAmount(int max, boolean zero) {
        int r;
        if (!zero) {
            r = new Random().nextInt(max) + 1;
        } else {
            r = new Random().nextInt(max);
        }
        return setAmount(r);
    }

    public ItemBuilder randomAmount(int max) {
        int r = new Random().nextInt(max) + 1;
        return setAmount(r);
    }

    public ItemBuilder chanceOfThousandBirk(int chance) {
        var a = new Random().nextInt(1000) + 1;
        if (a <= chance) {
            setAmount(1);
        } else {
            setAmount(0);
        }
        return this;
    }

    public ItemBuilder setDamage(int i) {
        if (meta instanceof Damageable d) {
            d.setDamage(i);
        }
        return this;
    }

    public ItemBuilder setDamage(int i, boolean random) {
        if (meta instanceof Damageable d) {
            if (random) {
                var c = new Random().nextInt(i);
                d.setDamage(c);
            } else {
                d.setDamage(i);
            }
        }
        return this;
    }

    public ItemBuilder randomEnchantment(int chance, Enchantment[] enchantments) {

        var random = new Random().nextInt(100) + 1;
        var current = new Random().nextInt(enchantments.length);

        if (random < chance) {
            addEnchantment(enchantments[current], new Random().nextInt(enchantments[current].getMaxLevel()) + 1);
        }

        return this;
    }

    public ItemBuilder setName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    public enum TypePotion {

        POTION,
        SPLASH,
        LINGERING

    }

    public static class PotionBuilder {

        ItemStack item;
        PotionMeta meta;

        public PotionBuilder(TypePotion type) {
            if (type == TypePotion.POTION) {
                item = new ItemStack(Material.POTION);
                meta = (PotionMeta) item.getItemMeta();
            }
            if (type == TypePotion.SPLASH) {
                item = new ItemStack(Material.SPLASH_POTION);
                meta = (PotionMeta) item.getItemMeta();
            }
            if (type == TypePotion.LINGERING) {
                item = new ItemStack(Material.LINGERING_POTION);
                meta = (PotionMeta) item.getItemMeta();
            }
        }

        public PotionBuilder randomItemAmount(int max, boolean zero) {
            int r;
            if (!zero) {
                r = new Random().nextInt(max) + 1;
            } else {
                r = new Random().nextInt(max);
            }
            return setAmount(r);
        }

        public PotionBuilder setAmount(int amount) {
            item.setAmount(amount);
            return this;
        }

        public PotionBuilder setName(String name) {
            meta.setDisplayName(name);
            return this;
        }

        public PotionBuilder addPotion(PotionEffect effect) {
            meta.addCustomEffect(effect, true);
            return this;
        }

        public PotionBuilder addPotion(PotionEffect... effect) {
            for (PotionEffect f : effect) {
                meta.addCustomEffect(f, true);
            }
            return this;
        }

        public PotionBuilder romanIndulgesRandom(int amount) {
            boolean give = new Random().nextBoolean();
            return give ? randomItemAmount(amount, false) : setAmount(0);
        }

        public PotionBuilder romanIndulges(int amount) {
            boolean give = new Random().nextBoolean();
            return give ? setAmount(amount) : setAmount(0);
        }

        public PotionBuilder romanIndulges(int amount, boolean random) {
            boolean give = new Random().nextBoolean();
            if (random) {
                var i = new Random().nextInt(amount) + 1;
                return give ? setAmount(i) : setAmount(0);
            }
            return give ? setAmount(amount) : setAmount(0);
        }

        public PotionBuilder romanIndulges() {
            boolean give = new Random().nextBoolean();
            return give ? setAmount(1) : setAmount(0);
        }

        public PotionBuilder setColor(Color color) {
            meta.setColor(color);
            return this;
        }

        public PotionBuilder setDataContainer(NamespacedKey key) {
            getMeta().getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
            return this;
        }

        public ItemStack build() {
            item.setItemMeta(meta);
            return item;
        }

        public PotionMeta getMeta() {
            return meta;
        }

    }

}
