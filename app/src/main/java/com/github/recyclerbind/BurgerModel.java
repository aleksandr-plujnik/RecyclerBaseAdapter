package com.github.recyclerbind;

import android.support.annotation.NonNull;

/**
 * Created on 24.04.2017
 * Project RecyclerBind
 *
 * @author Aleks Sander
 */

final class BurgerModel {
    double price;
    String name;

    BurgerModel(@NonNull String name) {
        this.name = name;
        this.price = name.length()/10.d;
    }

    @Override
    public String toString() {
        return "{" + name + " (" + price + ")}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BurgerModel model = (BurgerModel) o;

        return name.equals(model.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
