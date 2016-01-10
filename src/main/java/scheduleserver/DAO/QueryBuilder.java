package scheduleserver.DAO;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class QueryBuilder {

    private QueryBuilder() {
    }

    public static Builder newBuilder() {
        return new QueryBuilder().new Builder();
    }

    public class Builder {
        private List<Pair> criteriaList = new LinkedList<>();
        private List<Pair> condition = new LinkedList<>();


        private Builder() {

        }

        public Builder add(String key, String value) {
            criteriaList.add(new Pair(key, value));
            return this;
        }
        public Builder add(String key, Integer value) {
            criteriaList.add(new Pair(key, value.toString()));
            return this;
        }
        public Builder addCondition(String key, String value) {
            condition.add(new Pair(key, value));
            return this;
        }
        public Builder addCondition(String key, Integer value) {
            condition.add(new Pair(key, value.toString()));
            return this;
        }

        public String build(String tableName, CRUD crud) {
            switch (crud) {
                case CREATE:
                    return buildCreate(tableName);
                case READ:
                    return buildRead(tableName);
                case UPDATE:
                    return buildUpdate(tableName);
                case DELETE:
                    return buildDelete(tableName);
            }
            return null;
        }

        private String buildCreate(String tableName) {
            StringBuilder queryCreate = new StringBuilder("insert into ");
            queryCreate.append(tableName).append(" ").append("(");
            Iterator<Pair> iterator = criteriaList.iterator();
            while (iterator.hasNext()) {
                Pair pair = iterator.next();
                queryCreate.append(pair.getKey());
                if (iterator.hasNext()) {
                    queryCreate.append(",");
                }
            }
            queryCreate.append(") values (");

            iterator = criteriaList.iterator();
            while (iterator.hasNext()) {
                Pair pair = iterator.next();
                queryCreate.append(pair.getValue());
                if (iterator.hasNext()) {
                    queryCreate.append(",");
                }

            }
            queryCreate.append(")");
            return queryCreate.toString();
        }

        private String buildRead(String tableName) {
            StringBuilder queryRead = new StringBuilder("select * from ");

            queryRead.append(tableName).append(" ");

            prepareCriterias(criteriaList);
            if (criteriaList.size() == 0) {
                return queryRead.toString();
            }
            queryRead.append("where").append(" ");
            Iterator<Pair> iterator = criteriaList.iterator();
            while (iterator.hasNext()) {
                Pair pair = iterator.next();
                queryRead.append(pair.getKey()).append("=").append(pair.getValue()).append(" ");
                if (iterator.hasNext()) {
                    queryRead.append("and").append(" ");
                }
            }
            return queryRead.toString();
        }

        private String buildUpdate(String tableName) {
            StringBuilder queryUpdate = new StringBuilder("update").append(" ");
            queryUpdate.append(tableName).append(" ").append("set").append(" ");
            Iterator<Pair> iterator = criteriaList.iterator();
            while (iterator.hasNext()) {
                Pair pair = iterator.next();
                queryUpdate.append(pair.getKey());
                queryUpdate.append("=");
                queryUpdate.append(pair.getValue());
                if (iterator.hasNext()) {
                    queryUpdate.append(",");
                }
            }
            queryUpdate.append(" ").append("where").append(" ");
            iterator = condition.iterator();
            while (iterator.hasNext())
            {
                Pair pair = iterator.next();
                queryUpdate.append(pair.getKey()).append("=").append(pair.getValue());
                if(iterator.hasNext())
                {
                    queryUpdate.append(" and ");
                }
            }

            return queryUpdate.toString();
        }

        private String buildDelete(String tableName) {
            StringBuilder queryDelete = new StringBuilder("delete from ");
            queryDelete.append(tableName).append(" ");
            prepareCriterias(criteriaList);
            if (criteriaList.size() == 0) {
                return queryDelete.toString();
            }
            queryDelete.append("where").append(" ");
            Iterator<Pair> iterator = criteriaList.iterator();
            while (iterator.hasNext()) {
                Pair pair = iterator.next();
                queryDelete.append(pair.getKey()).append("=").append(pair.getValue()).append(" ");
                if (iterator.hasNext()) {
                    queryDelete.append("and").append(" ");
                }
            }
            return queryDelete.toString();

        }


        private boolean isAll(Pair pair) {
            return pair.getValue().contains(DAOConstants.ALL_VALUES_IDENTIFIER);
        }

        private void prepareCriterias(List<Pair> list) {
            Iterator<Pair> iterator = criteriaList.iterator();
            while (iterator.hasNext()) {
                if (isAll(iterator.next())) {
                    iterator.remove();
                }
            }
        }

    }

    private class Pair {
        private String key;
        private String value;

        public Pair(String key, String value) {
            this.key = "`"+key+"`";
            this.value = "'"+value+"'";
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

    }
}
