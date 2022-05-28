package com.github.blacksabin.orphic.anima;

public interface AnimaInterface {

    AnimaPropertiesContainer animaPropertiesContainer = new AnimaPropertiesContainer();

    default AnimaPropertiesContainer orphic$getAnimaProperties(){
        return this.animaPropertiesContainer;
    }

}
