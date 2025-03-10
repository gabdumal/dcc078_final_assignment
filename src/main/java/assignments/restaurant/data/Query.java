/*
 * Copyright (c) 2025 Gabriel Malosto.
 *
 * Licensed under the GNU Affero General Public License, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <https://www.gnu.org/licenses/agpl-3.0.txt>.
 */

package assignments.restaurant.data;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Query {

    private static final Data data = Data.getInstance();

    private Query() {
        throw new IllegalStateException("This is a utility class!");
    }

    private static CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> and(
            CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> first,
            CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> second
                                                                                      ) {
        var result = new CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>>();
        for (var list : first) {
            if (second.contains(list)) {
                result.add(list);
            }
        }
        return result;
    }

    public static CopyOnWriteArrayList<MenuComponentRecord> fetchAllMenuComponents(
            RestrictByCuisine restrictByCuisine,
            RestrictByCategory restrictByCategory,
            RestrictByDecorator restrictByDecorator
                                                                                  ) {
        var lists = new CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>>();

        lists = and(
                and(restrictByCuisine(restrictByCuisine), restrictByCategory(restrictByCategory)),
                restrictByDecorator(restrictByDecorator)
                   );

        return lists.stream()
                    .flatMap(CopyOnWriteArrayList::stream)
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    public static CopyOnWriteArrayList<MenuComponentRecord> fetchMenuComponentById(
            String restrictById
                                                                                  ) {
        var lists = new CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>>();

        if (restrictById != null && !restrictById.isBlank()) {
            lists = restrictById(restrictById);
        }

        return lists.stream()
                    .flatMap(CopyOnWriteArrayList::stream)
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    public static CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> restrictById(String id) {
        var result = new CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>>();
        for (var list : returnAllLists()) {
            for (var record : list) {
                if (record.id().equals(id)) {
                    result.add(list);
                }
            }
        }
        return result;
    }

    private static CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> returnAllLists() {
        var result = new CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>>();
        returnAllBrazilianCuisineMenuComponents(result);
        returnAllItalianCuisineMenuComponents(result);
        return result;
    }

    private static void returnAllBrazilianCuisineMenuComponents(CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> result) {
        result.add(data.getBrazilianCuisineAppetizers());
        result.add(data.getBrazilianCuisineAppetizersDecorators());
        result.add(data.getBrazilianCuisineBeverages());
        result.add(data.getBrazilianCuisineBeveragesDecorators());
        result.add(data.getBrazilianCuisineDesserts());
        result.add(data.getBrazilianCuisineDessertsDecorators());
        result.add(data.getBrazilianCuisineMainCourses());
        result.add(data.getBrazilianCuisineMainCoursesDecorators());
    }

    private static void returnAllItalianCuisineMenuComponents(CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> result) {
        result.add(data.getItalianCuisineAppetizers());
        result.add(data.getItalianCuisineAppetizersDecorators());
        result.add(data.getItalianCuisineBeverages());
        result.add(data.getItalianCuisineBeveragesDecorators());
        result.add(data.getItalianCuisineDesserts());
        result.add(data.getItalianCuisineDessertsDecorators());
        result.add(data.getItalianCuisineMainCourses());
        result.add(data.getItalianCuisineMainCoursesDecorators());
    }

    private static CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> restrictByCategory(
            RestrictByCategory restrictByCategory
                                                                                                     ) {
        var result = new CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>>();
        switch (restrictByCategory) {
            case Appetizer -> {
                result.add(data.getBrazilianCuisineAppetizers());
                result.add(data.getBrazilianCuisineAppetizersDecorators());
                result.add(data.getItalianCuisineAppetizers());
                result.add(data.getItalianCuisineAppetizersDecorators());
            }
            case Beverage -> {
                result.add(data.getBrazilianCuisineBeverages());
                result.add(data.getBrazilianCuisineBeveragesDecorators());
                result.add(data.getItalianCuisineBeverages());
                result.add(data.getItalianCuisineBeveragesDecorators());
            }
            case Dessert -> {
                result.add(data.getBrazilianCuisineDesserts());
                result.add(data.getBrazilianCuisineDessertsDecorators());
                result.add(data.getItalianCuisineDesserts());
                result.add(data.getItalianCuisineDessertsDecorators());
            }
            case MainCourse -> {
                result.add(data.getBrazilianCuisineMainCourses());
                result.add(data.getBrazilianCuisineMainCoursesDecorators());
                result.add(data.getItalianCuisineMainCourses());
                result.add(data.getItalianCuisineMainCoursesDecorators());
            }
            case null -> {
                result.addAll(returnAllLists());
            }
        }
        return result;
    }

    private static CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> restrictByCuisine(
            RestrictByCuisine restrictByCuisine
                                                                                                    ) {
        var result = new CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>>();
        switch (restrictByCuisine) {
            case Brazilian -> {
                returnAllBrazilianCuisineMenuComponents(result);
            }
            case Italian -> {
                returnAllItalianCuisineMenuComponents(result);
            }
            case null -> {
                result.addAll(returnAllLists());
            }
        }
        return result;
    }

    private static CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>> restrictByDecorator(
            RestrictByDecorator restrictByDecorator
                                                                                                      ) {
        var result = new CopyOnWriteArrayList<CopyOnWriteArrayList<MenuComponentRecord>>();
        switch (restrictByDecorator) {
            case IsDecorator -> {
                result.add(data.getBrazilianCuisineAppetizersDecorators());
                result.add(data.getBrazilianCuisineBeveragesDecorators());
                result.add(data.getBrazilianCuisineDessertsDecorators());
                result.add(data.getBrazilianCuisineMainCoursesDecorators());
                result.add(data.getItalianCuisineAppetizersDecorators());
                result.add(data.getItalianCuisineBeveragesDecorators());
                result.add(data.getItalianCuisineDessertsDecorators());
                result.add(data.getItalianCuisineMainCoursesDecorators());
            }
            case IsNotDecorator -> {
                result.add(data.getBrazilianCuisineAppetizers());
                result.add(data.getBrazilianCuisineBeverages());
                result.add(data.getBrazilianCuisineDesserts());
                result.add(data.getBrazilianCuisineMainCourses());
                result.add(data.getItalianCuisineAppetizers());
                result.add(data.getItalianCuisineBeverages());
                result.add(data.getItalianCuisineDesserts());
                result.add(data.getItalianCuisineMainCourses());
            }
            case null -> {
                result.addAll(returnAllLists());
            }
        }
        return result;
    }

}