/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.data;

import assignments.restaurant.component.CategoryType;
import assignments.restaurant.cuisine.CuisineType;

import java.util.concurrent.CopyOnWriteArrayList;

public class Data {

    private static Data                                      instance;
    private final  CopyOnWriteArrayList<MenuComponentRecord> brazilianCuisineAppetizers            = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> brazilianCuisineAppetizersDecorators  = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> brazilianCuisineBeverages             = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> brazilianCuisineBeveragesDecorators   = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> brazilianCuisineDesserts              = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> brazilianCuisineDessertsDecorators    = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> brazilianCuisineMainCourses           = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> brazilianCuisineMainCoursesDecorators = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> italianCuisineAppetizers              = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> italianCuisineAppetizersDecorators    = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> italianCuisineBeverages               = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> italianCuisineBeveragesDecorators     = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> italianCuisineDesserts                = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> italianCuisineDessertsDecorators      = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> italianCuisineMainCourses             = new CopyOnWriteArrayList<>();
    private final  CopyOnWriteArrayList<MenuComponentRecord> italianCuisineMainCoursesDecorators   = new CopyOnWriteArrayList<>();

    private Data() {
        this.populateMenuComponentsRecords();
    }

    private void populateMenuComponentsRecords() {
        this.populateBrazilianCuisineAppetizers();
        this.populateBrazilianCuisineAppetizersDecorators();
        this.populateBrazilianCuisineBeverages();
        this.populateBrazilianCuisineBeveragesDecorators();
        this.populateBrazilianCuisineDesserts();
        this.populateBrazilianCuisineDessertsDecorators();
        this.populateBrazilianCuisineMainCourses();
        this.populateBrazilianCuisineMainCoursesDecorators();

        this.populateItalianCuisineAppetizers();
        this.populateItalianCuisineAppetizersDecorators();
        this.populateItalianCuisineBeverages();
        this.populateItalianCuisineBeveragesDecorators();
        this.populateItalianCuisineDesserts();
        this.populateItalianCuisineDessertsDecorators();
        this.populateItalianCuisineMainCourses();
        this.populateItalianCuisineMainCoursesDecorators();
    }

    private void populateBrazilianCuisineAppetizers() {
        brazilianCuisineAppetizers.add(new MenuComponentRecord(
                "pao_de_alho",
                CategoryType.Appetizer,
                CuisineType.Brazilian,
                false,
                "Pão de alho",
                "Pão francês assado ao molho de alho, azeite e ervas.",
                6.0d
        ));
        brazilianCuisineAppetizers.add(new MenuComponentRecord(
                "coxinha",
                CategoryType.Appetizer,
                CuisineType.Brazilian,
                false,
                "Coxinha",
                "Massa de batata recheada com frango desfiado.",
                5.0d
        ));
        brazilianCuisineAppetizers.add(new MenuComponentRecord(
                "pastel",
                CategoryType.Appetizer,
                CuisineType.Brazilian,
                false,
                "Pastel",
                "Massa de pastel recheada com carne moída.",
                4.0d
        ));
        brazilianCuisineAppetizers.add(new MenuComponentRecord(
                "caldo_de_feijao",
                CategoryType.Appetizer,
                CuisineType.Brazilian,
                false,
                "Caldo de feijão",
                "Caldo de feijão temperado com bacon e linguiça.",
                12.0d
        ));
    }

    private void populateBrazilianCuisineAppetizersDecorators() {
        brazilianCuisineAppetizersDecorators.add(new MenuComponentRecord(
                "mucarela",
                CategoryType.Appetizer,
                CuisineType.Brazilian,
                true,
                "Muçarela",
                "Fatias finas de queijo muçarela.",
                2.0d
        ));
    }

    private void populateBrazilianCuisineBeverages() {
        brazilianCuisineBeverages.add(new MenuComponentRecord(
                "caipirinha",
                CategoryType.Beverage,
                CuisineType.Brazilian,
                false,
                "Caipirinha",
                "Cachaça, limão, açúcar e gelo.",
                10.0d
        ));
    }

    private void populateBrazilianCuisineBeveragesDecorators() {
        brazilianCuisineBeveragesDecorators.add(new MenuComponentRecord(
                "mel",
                                                                        CategoryType.Beverage,
                                                                        CuisineType.Brazilian,
                                                                        true,
                                                                        "Mel",
                                                                        "Mel puro.",
                                                                        3.0d
        ));
    }

    private void populateBrazilianCuisineDesserts() {
        brazilianCuisineDesserts.add(new MenuComponentRecord(
                "brigadeiro",
                CategoryType.Dessert,
                CuisineType.Brazilian,
                false,
                "Brigadeiro",
                "Doce de chocolate com leite condensado e chocolate granulado.",
                5.0d
        ));
    }

    private void populateBrazilianCuisineDessertsDecorators() {
        brazilianCuisineDessertsDecorators.add(new MenuComponentRecord(
                "doce_de_leite",
                CategoryType.Dessert,
                CuisineType.Brazilian,
                true,
                "Doce de leite",
                "Doce de leite pastoso.",
                4.0d
        ));
    }

    private void populateBrazilianCuisineMainCourses() {
        brazilianCuisineMainCourses.add(new MenuComponentRecord(
                "feijoada",
                CategoryType.MainCourse,
                CuisineType.Brazilian,
                false,
                "Feijoada",
                "Feijoada completa com arroz, couve, farofa, laranja e torresmo.",
                30.0d
        ));
    }

    private void populateBrazilianCuisineMainCoursesDecorators() {
        brazilianCuisineMainCoursesDecorators.add(new MenuComponentRecord(
                "farofa",
                CategoryType.MainCourse,
                CuisineType.Brazilian,
                true,
                "Farofa",
                "Farinha de mandioca torrada com bacon e ovos.",
                5.0d
        ));
    }

    private void populateItalianCuisineAppetizers() {
        italianCuisineAppetizers.add(new MenuComponentRecord(
                "bruschetta",
                CategoryType.Appetizer,
                CuisineType.Italian,
                false,
                "Bruschetta",
                "Pão italiano torrado com tomate, manjericão e azeite.",
                10.0d
        ));
        italianCuisineAppetizers.add(new MenuComponentRecord(
                "carpaccio",
                CategoryType.Appetizer,
                CuisineType.Italian,
                false,
                "Carpaccio",
                "Fatias finas de carne crua temperadas com molho de mostarda.",
                15.0d
        ));
        italianCuisineAppetizers.add(new MenuComponentRecord(
                "focaccia",
                CategoryType.Appetizer,
                CuisineType.Italian,
                false,
                "Focaccia",
                "Pão italiano achatado com azeite e alecrim.",
                8.0d
        ));
    }

    private void populateItalianCuisineAppetizersDecorators() {
        italianCuisineAppetizersDecorators.add(new MenuComponentRecord(
                "molho_pesto",
                CategoryType.Appetizer,
                CuisineType.Italian,
                true,
                "Molho pesto",
                "Molho de manjericão, azeite, alho, queijo parmesão e pinoli.",
                5.0d
        ));
    }

    private void populateItalianCuisineBeverages() {
        italianCuisineBeverages.add(new MenuComponentRecord(
                "vinho_chianti",
                CategoryType.Beverage,
                CuisineType.Italian,
                false,
                "Vinho Chianti",
                "Vinho tinto seco.",
                50.0d
        ));
    }

    private void populateItalianCuisineBeveragesDecorators() {
        italianCuisineBeveragesDecorators.add(new MenuComponentRecord(
                "gelo",
                                                                      CategoryType.Beverage,
                                                                      CuisineType.Italian,
                                                                      true,
                                                                      "Gelo",
                                                                      "Gelo em cubos.",
                                                                      1.0d
        ));
    }

    private void populateItalianCuisineDesserts() {
        italianCuisineDesserts.add(new MenuComponentRecord(
                "tiramisu",
                CategoryType.Dessert,
                CuisineType.Italian,
                false,
                "Tiramisù",
                "Sobremesa italiana à base de café, biscoitos champanhe, queijo mascarpone e cacau.",
                20.0d
        ));
    }

    private void populateItalianCuisineDessertsDecorators() {
        italianCuisineDessertsDecorators.add(new MenuComponentRecord(
                "cereja",
                                                                     CategoryType.Dessert,
                                                                     CuisineType.Italian,
                                                                     true,
                                                                     "Cereja",
                                                                     "Cereja em calda.",
                                                                     3.0d
        ));
    }

    private void populateItalianCuisineMainCourses() {
        italianCuisineMainCourses.add(new MenuComponentRecord(
                "fettuccine_alfredo",
                CategoryType.MainCourse,
                CuisineType.Italian,
                false,
                "Fettuccine Alfredo",
                "Massa fettuccine ao molho branco com queijo parmesão.",
                25.0d
        ));
    }

    private void populateItalianCuisineMainCoursesDecorators() {
        italianCuisineMainCoursesDecorators.add(new MenuComponentRecord(
                "molho_bolonhesa",
                CategoryType.MainCourse,
                CuisineType.Italian,
                true,
                "Molho bolonhesa",
                "Molho de tomate com carne moída.",
                7.0d
        ));
    }

    public static synchronized Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getBrazilianCuisineAppetizers() {
        return brazilianCuisineAppetizers;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getBrazilianCuisineAppetizersDecorators() {
        return brazilianCuisineAppetizersDecorators;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getBrazilianCuisineBeverages() {
        return brazilianCuisineBeverages;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getBrazilianCuisineBeveragesDecorators() {
        return brazilianCuisineBeveragesDecorators;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getBrazilianCuisineDesserts() {
        return brazilianCuisineDesserts;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getBrazilianCuisineDessertsDecorators() {
        return brazilianCuisineDessertsDecorators;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getBrazilianCuisineMainCourses() {
        return brazilianCuisineMainCourses;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getBrazilianCuisineMainCoursesDecorators() {
        return brazilianCuisineMainCoursesDecorators;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getItalianCuisineAppetizers() {
        return italianCuisineAppetizers;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getItalianCuisineAppetizersDecorators() {
        return italianCuisineAppetizersDecorators;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getItalianCuisineBeverages() {
        return italianCuisineBeverages;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getItalianCuisineBeveragesDecorators() {
        return italianCuisineBeveragesDecorators;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getItalianCuisineDesserts() {
        return italianCuisineDesserts;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getItalianCuisineDessertsDecorators() {
        return italianCuisineDessertsDecorators;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getItalianCuisineMainCourses() {
        return italianCuisineMainCourses;
    }

    public CopyOnWriteArrayList<MenuComponentRecord> getItalianCuisineMainCoursesDecorators() {
        return italianCuisineMainCoursesDecorators;
    }

}
