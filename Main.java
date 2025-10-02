
import java.io.*;


class Main
{

    public static void main(String[] args) throws Exception {
        Util.reader("src/database.db");
        run();

        System.out.println(Util.Index);
    }


    public static void run() throws Exception {
        while(true)
        {
            System.out.print("db> ");
            String x = Util.sc.nextLine();
            String[] parts = x.split(" ");
            switch (parts[0])
            {
                case "insert": insert(parts);break;

                case "select": select(parts);break;

                case "delete": delete(parts);break;

                case "update": update(parts);break;
                case "exit": System.out.println("Exiting");return;

                default: System.out.println("Invalid command!");break;
            }
        }
    }

    public static void insert(String[] parts)
    {
        Util.lock.writeLock().lock();
        try {

            if (parts.length != 4) {
                System.out.println("Invalid arguments-> argumets should be of length 4");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(parts[1]);
            } catch (NumberFormatException ne) {
                System.out.println("Id should be a number!!");
                return;
            }
            if (id < 0) {
                System.out.println("Invalid id>0");
                return;
            }
            Row r = new Row(id, parts[2], parts[3]);
            Util.Index.put(r.id, r);
            System.out.println("Executed");
            try {
                Util.add("src/database.db");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        finally {
            Util.lock.writeLock().unlock();
        }
    }

    public static void delete(String[] parts) throws Exception
    {
        Util.lock.writeLock().lock();
        try {
            switch (parts[1]) {
                case "where": {
                    switch (parts[2]) {
                        case "id": {
                            switch (parts[3]) {
                                case "=":
                                    Util.Index.remove(Integer.parseInt(parts[4]));
                                    Util.add("src/database.db");
                                    break;

                                case ">":
                                    Util.Index.keySet().removeIf(i -> i > Integer.parseInt(parts[4]));
                                    Util.add("src/database.db");
                                    break;

                                case "<":
                                    Util.Index.keySet().removeIf(i -> i < Integer.parseInt(parts[4]));
                                    Util.add("src/database.db");
                                    break;

                                default:
                                    System.out.println("Invalid symbol");
                                    break;
                            }
                            break;
                        }
                        default:
                            System.out.println("Invalid member");
                            break;
                    }
                }
            }
        }
        finally {
            Util.lock.writeLock().unlock();
        }
    }
    public static void select(String[] parts)
    {
        Util.lock.readLock().lock();
        try {

            if (parts.length == 1) {
                if (Util.Index.isEmpty()) {
                    System.out.println("0 rows");
                    return;
                }
                for (Row row : Util.Index.values()) System.out.println(row);
                return;
            }
            switch (parts[1]) {
                case "where":
                    where(parts);
                    break;
                default:
                    System.out.println("Invalid operation");
                    break;
            }
        }
        finally {
            Util.lock.readLock().unlock();
        }
    }

    public static void where(String[] parts)
    {
        switch(parts[2])
        {
            case "id":
            {
                switch (parts[3])
                {
                    case "=" :
                    {
                        if(Util.Index.containsKey(Integer.parseInt(parts[4])))System.out.println(Util.Index.get(Integer.parseInt(parts[4])));
                        else System.out.println("Not Found");
                        break;
                    }
                    case ">" :
                    {
                        for(Row row : Util.Index.tailMap(Integer.parseInt(parts[4]),false).values() ) System.out.println(row);
                        break;
                    }
                    case "<":
                    {
                        for(Row row : Util.Index.headMap(Integer.parseInt(parts[4]),false).values()) System.out.println(row);
                        break;
                    }
                    default:
                        System.out.println("Invalid symbol");break;
                }
                break;
            }
            default:
                System.out.println("Invalid member");break;
        }
    }

    public static void update(String[] parts) throws Exception {

        Util.lock.writeLock().lock();
        try {
            if (parts.length != 4) {
                System.out.println("Invalid Arguments. Should be of the form: update <id> set <member>=<val> .");
                return; // Use 'return' to exit the method early if validation fails
            }
            if (!parts[2].equals("set")) {
                System.out.println("Invalid Syntax. Missing 'set' keyword.");
                return;
            }

            int idToUpdate;
            try {
                idToUpdate = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid id. Must be a number!");
                return;
            }
            Row rowToUpdate = Util.Index.get(idToUpdate);
            if (rowToUpdate == null) {
                System.out.println("Id not found!!");
                return;
            }
            String[] mv = parts[3].split("=", 2);
            if (mv.length < 2) {
                System.out.println("Invalid set clause. Should be in the form: name=value");
                return;
            }
            String column = mv[0];
            String value = mv[1];
            switch (column) {
                case "email":
                    rowToUpdate.email = value;
                    break;
                case "name":
                    rowToUpdate.name = value;
                    break;
                default:
                    System.out.println("Invalid column name: " + column);
                    return;
            }

            Util.add("src/database.db");
            System.out.println("Updated");

        }
        finally
        {
            Util.lock.writeLock().unlock();
        }

    }
}

