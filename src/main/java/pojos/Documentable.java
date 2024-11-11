
package pojos;

import org.bson.Document;

/**
 * Permite que un POJO pueda ser serializado a JSON
 * @author neri
 */
public interface Documentable {
    public Document toDocument();
}
