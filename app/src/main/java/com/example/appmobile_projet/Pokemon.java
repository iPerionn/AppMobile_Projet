package com.example.appmobile_projet;

import androidx.annotation.NonNull;

public class Pokemon {
    private int id;
    private String name;
    private  String imageURL;
    private Integer hp;
    private Integer attack;
    private Integer defense;
    private Integer attack_spe;
    private Integer defense_spe;
    private Integer speed;

    /**
     * Création de l'objet pokémon
     * @param id
     * @param name
     * @param image
     * @param hp
     * @param attack
     * @param defense
     * @param attack_spe
     * @param defense_spe
     * @param speed
     */
    public Pokemon(int id,String name, String image, Integer hp, Integer attack, Integer defense, Integer attack_spe, Integer defense_spe, Integer speed) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.imageURL = image;
        this.attack = attack;
        this.defense = defense;
        this.attack_spe = attack_spe;
        this.defense_spe = defense_spe;
        this.speed = speed;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getAttack_spe() {
        return attack_spe;
    }

    public void setAttack_spe(Integer attack_spe) {
        this.attack_spe = attack_spe;
    }

    public Integer getDefense_spe() {
        return defense_spe;
    }

    public void setDefense_spe(Integer defense_spe) {
        this.defense_spe = defense_spe;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    /**
     *
     * @param i
     * @return une caractéristique d'un pokémon
     */
    public int getCarctFromInt(int i){
        switch (i) {
            case 1 :
                return getAttack();
            case 2 :
                return getAttack_spe();
            case 3 :
                return getDefense_spe();
            case 4 :
                return getDefense();
            case 5 :
                return getHp();
            case 6 :
                return getSpeed();
        }
        return getDefense();
    }

    /**
     *
     * @return les détails d'un pokémon en chaine de caractères
     */
    @Override
    public String toString(){
        return " Points de vie : " + "\t"+ this.getHp() +
                "\n Attaque : " + "\t"+ this.getAttack() +
                "\n Défense : " + "\t"+ this.getDefense() +
                "\n Attaque spéciale : " + "\t"+ this.getAttack_spe() +
                "\n Défense spéciale : " + "\t"+ this.getDefense_spe() +
                "\n Vitesse : " + "\t"+ this.getSpeed();
    }
}
