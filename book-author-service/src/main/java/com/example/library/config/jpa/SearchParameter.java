package com.example.library.config.jpa;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Parameter(in = ParameterIn.QUERY, name ="search" ,schema = @Schema(type = "string"),
        description = "### Поисковая спецификация\n" +
                "Представляет собой строку поиска по модели с указанием имени атрибута модели (с возможностью поиска по отношению), оператора сравнения и логического оператора.\n" +
                "формат: *{атрибут модели}\\_\\_{атрибут отношения}\\_\\_{атрибут модели отношения}{оператор сравнения}{значение}{логический оператор}*\n" +
                "#### Операторы сравнения\n" +
                "- : равно (для строк - включение (LIKE) без учета регистра)\n" +
                "- :: равно (для строк -  равно)\n" +
                "- \\> больше\n" +
                "- < меньше\n" +
                "- \\>- больше или равно\n" +
                "- <- меньше или равно\n" +
                "- ?> больше или равно NULL\n" +
                "- ?< меньше или равно NULL\n" +
                "- ! не равно\n" +
                "- \\>> равно в коллекции значений, указание значений через запятую\n" +
                "#### Логические операторы\n" +
                "- ^ И\n" +
                "- || ИЛИ\n" +
                "#### Модель\n" +
                "```\n" +
                "class User {\n" +
                "  String name;\n" +
                "  UserGroup group;\n" +
                "}\n" +
                "class UserGroup {\n" +
                "  UserGroupType groupType; // ENUM (USER, ADMIN)\n" +
                "}\n" +
                "``` \n" +
                "- Найти пользователей с именем, включающим символы \"Ива\", тогда  *search=name:Ива*\n" +
                "- Найти администраторов с именем, включающим символы \"Ива\", тогда *search=name:Ива^group__groupType:ADMIN*\n")
public @interface SearchParameter {
}
