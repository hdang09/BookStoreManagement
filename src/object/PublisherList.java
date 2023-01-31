package object;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PublisherList extends ArrayList<Publisher> {
    String file = "src\\data\\Publisher.dat";

    public void create(Publisher publisher) {
        this.add(publisher);
    }

    public void delete(int deleteIndex) {
        this.forEach(System.out::println);

        if (deleteIndex != -1) {
            this.remove(deleteIndex);
            System.out.println("Delete succesfully");
            this.saveToFile();
        } else {
            System.err.println("Publisher's Id does not exist");
        }
    }

    public void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            try (ObjectOutputStream obj = new ObjectOutputStream(fos)) {
                for (Publisher pu : this) {
                    obj.writeObject(pu);
                }
                System.out.println("Write to file successfully");
                obj.close();
                fos.close();
            }

            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PublisherList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PublisherList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PublisherList readFile() {
        File f = new File(file);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(PublisherList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            try (ObjectInputStream obj = new ObjectInputStream(fis)) {
                boolean hasNext = true;
                while (hasNext) {
                    if (fis.available() != 0) {
                        Publisher pu = (Publisher) obj.readObject();
                        this.add(pu);
                    } else {
                        hasNext = false;
                    }
                }
                obj.close();
            } catch (EOFException e) {
                return this;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PublisherList.class.getName()).log(Level.SEVERE, null, ex);
            return this;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PublisherList.class.getName()).log(Level.SEVERE, null, ex);
            return this;
        }
        return this;
    }

    public void printFromFile() {
        if (this.isEmpty()) {
            System.err.println("The list is empty!");
        } else {
            Collections.sort(this);
            System.out.println("---------------------------------------------------------------");
            System.out.println("| PublisherID |              Name              |     Phone    |");
            System.out.println("|-------------|--------------------------------|--------------|");
            this.forEach(System.out::println);
            System.out.println("---------------------------------------------------------------");
        }
    }
}
