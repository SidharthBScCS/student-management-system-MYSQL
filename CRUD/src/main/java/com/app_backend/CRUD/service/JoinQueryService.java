package com.app_backend.CRUD.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JoinQueryService {

    private final JdbcTemplate jdbcTemplate;

    private static final List<String> JOIN_TYPES = List.of("INNER JOIN", "LEFT JOIN", "RIGHT JOIN");

    private final Map<String, TableDefinition> tableDefinitions = Map.of(
            "student", new TableDefinition("student", "s", List.of(
                    new ColumnDefinition("id", "ID"),
                    new ColumnDefinition("name", "Name"),
                    new ColumnDefinition("course", "Course"),
                    new ColumnDefinition("batch", "Batch"),
                    new ColumnDefinition("phone", "Phone"),
                    new ColumnDefinition("email", "Email"))),
            "faculty", new TableDefinition("faculty", "f", List.of(
                    new ColumnDefinition("id", "ID"),
                    new ColumnDefinition("name", "Name"),
                    new ColumnDefinition("department", "Department"),
                    new ColumnDefinition("designation", "Designation"),
                    new ColumnDefinition("phone", "Phone"),
                    new ColumnDefinition("email", "Email"))),
            "course", new TableDefinition("course", "c", List.of(
                    new ColumnDefinition("id", "ID"),
                    new ColumnDefinition("course_name", "Course Name"),
                    new ColumnDefinition("course_code", "Course Code"),
                    new ColumnDefinition("department", "Department"),
                    new ColumnDefinition("faculty_name", "Faculty Name"),
                    new ColumnDefinition("credits", "Credits"))),
            "department", new TableDefinition("department", "d", List.of(
                    new ColumnDefinition("id", "ID"),
                    new ColumnDefinition("department_name", "Department Name"),
                    new ColumnDefinition("department_code", "Department Code"),
                    new ColumnDefinition("head_of_department", "Head Of Department"),
                    new ColumnDefinition("building_name", "Building Name"),
                    new ColumnDefinition("floor_number", "Floor Number"))),
            "library", new TableDefinition("library", "l", List.of(
                    new ColumnDefinition("id", "ID"),
                    new ColumnDefinition("book_name", "Book Name"),
                    new ColumnDefinition("author_name", "Author Name"),
                    new ColumnDefinition("category", "Category"),
                    new ColumnDefinition("language", "Language"),
                    new ColumnDefinition("availability_status", "Availability Status"))));

    private final List<RelationDefinition> relationDefinitions = List.of(
            new RelationDefinition("student", "course", "course", "course_name",
                    "Students matched with their selected courses."),
            new RelationDefinition("course", "faculty", "faculty_name", "name",
                    "Courses matched with the faculty teaching them."),
            new RelationDefinition("faculty", "department", "department", "department_name",
                    "Faculty members matched with their departments."),
            new RelationDefinition("course", "department", "department", "department_name",
                    "Courses matched with the department offering them."));

    public JoinQueryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getTableNames() {
        return List.of("student", "faculty", "course", "department", "library");
    }

    public List<String> getJoinTypes() {
        return JOIN_TYPES;
    }

    public JoinQueryResult buildJoinResult(String table1, String table2, String joinType) {
        String normalizedTable1 = normalizeTableName(table1);
        String normalizedTable2 = normalizeTableName(table2);
        String normalizedJoinType = normalizeJoinType(joinType);

        if (normalizedTable1.equals(normalizedTable2)) {
            return unsupportedResult(
                    normalizedTable1,
                    normalizedTable2,
                    normalizedJoinType,
                    "Please choose two different tables for a join.");
        }

        RelationDefinition relation = findRelation(normalizedTable1, normalizedTable2);
        if (relation == null) {
            return unsupportedResult(
                    normalizedTable1,
                    normalizedTable2,
                    normalizedJoinType,
                    "This table pair does not have a direct relationship in the current project.");
        }

        TableDefinition leftTable = tableDefinitions.get(normalizedTable1);
        TableDefinition rightTable = tableDefinitions.get(normalizedTable2);

        String leftJoinColumn = relation.leftTable().equals(normalizedTable1)
                ? relation.leftColumn()
                : relation.rightColumn();
        String rightJoinColumn = relation.leftTable().equals(normalizedTable1)
                ? relation.rightColumn()
                : relation.leftColumn();

        List<String> columns = new ArrayList<>();
        List<DisplayColumn> displayColumns = new ArrayList<>();
        String selectClause = buildSelectClause(leftTable, rightTable, columns, displayColumns);

        String sql = """
                SELECT %s
                FROM %s %s
                %s %s %s
                    ON %s.%s = %s.%s
                ORDER BY %s.id
                """.formatted(
                selectClause,
                leftTable.tableName(), leftTable.alias(),
                normalizedJoinType, rightTable.tableName(), rightTable.alias(),
                leftTable.alias(), leftJoinColumn, rightTable.alias(), rightJoinColumn,
                leftTable.alias());

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        return new JoinQueryResult(
                normalizedTable1,
                normalizedTable2,
                normalizedJoinType,
                relation.description(),
                sql,
                results,
                displayColumns,
                true,
                null);
    }

    private String buildSelectClause(
            TableDefinition leftTable,
            TableDefinition rightTable,
            List<String> columns,
            List<DisplayColumn> displayColumns) {
        List<String> selectParts = new ArrayList<>();

        appendSelectParts(leftTable, columns, displayColumns, selectParts);
        appendSelectParts(rightTable, columns, displayColumns, selectParts);

        return String.join(",\n       ", selectParts);
    }

    private void appendSelectParts(
            TableDefinition table,
            List<String> columns,
            List<DisplayColumn> displayColumns,
            List<String> selectParts) {
        for (ColumnDefinition column : table.columns()) {
            String alias = table.tableName() + "_" + column.name();
            columns.add(alias);
            displayColumns.add(new DisplayColumn(alias, capitalize(table.tableName()) + " " + column.label()));
            selectParts.add(table.alias() + "." + column.name() + " AS " + alias);
        }
    }

    private RelationDefinition findRelation(String table1, String table2) {
        return relationDefinitions.stream()
                .filter(relation -> matchesRelation(relation, table1, table2))
                .findFirst()
                .orElse(null);
    }

    private boolean matchesRelation(RelationDefinition relation, String table1, String table2) {
        return (Objects.equals(relation.leftTable(), table1) && Objects.equals(relation.rightTable(), table2))
                || (Objects.equals(relation.leftTable(), table2) && Objects.equals(relation.rightTable(), table1));
    }

    private JoinQueryResult unsupportedResult(String table1, String table2, String joinType, String message) {
        return new JoinQueryResult(
                table1,
                table2,
                joinType,
                "Choose a supported table relationship to see SQL join output.",
                "",
                List.of(),
                List.of(),
                false,
                message);
    }

    private String normalizeTableName(String tableName) {
        if (tableDefinitions.containsKey(tableName)) {
            return tableName;
        }
        return "student";
    }

    private String normalizeJoinType(String joinType) {
        return JOIN_TYPES.stream()
                .filter(value -> value.equalsIgnoreCase(joinType))
                .findFirst()
                .orElse("INNER JOIN");
    }

    private String capitalize(String value) {
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    public record JoinQueryResult(
            String table1,
            String table2,
            String joinType,
            String description,
            String sql,
            List<Map<String, Object>> results,
            List<DisplayColumn> displayColumns,
            boolean supported,
            String message) {
    }

    public record DisplayColumn(String key, String label) {
    }

    private record TableDefinition(
            String tableName,
            String alias,
            List<ColumnDefinition> columns) {
    }

    private record ColumnDefinition(String name, String label) {
    }

    private record RelationDefinition(
            String leftTable,
            String rightTable,
            String leftColumn,
            String rightColumn,
            String description) {
    }
}
