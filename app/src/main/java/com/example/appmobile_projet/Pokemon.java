package com.example.appmobile_projet;

public class Pokemon {
    private String name;
    private  String imageURL;
    private Integer hp;
    private Integer attack;
    private Integer defense;
    private Integer attack_spe;
    private Integer defense_spe;
    private Integer speed;

    public Pokemon(String name, String image, Integer hp, Integer attack, Integer defense, Integer attack_spe, Integer defense_spe, Integer speed) {
        this.name = name;
        this.hp = hp;
        this.imageURL = image;
        this.attack = attack;
        this.defense = defense;
        this.attack_spe = attack_spe;
        this.defense_spe = defense_spe;
        this.speed = speed;
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
}
