package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Profesor type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Profesors", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Profesor implements Model {
  public static final QueryField ID = field("Profesor", "id");
  public static final QueryField NOMBRE = field("Profesor", "Nombre");
  public static final QueryField CARGO = field("Profesor", "Cargo");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String Nombre;
  private final @ModelField(targetType="String") String Cargo;
  private final @ModelField(targetType="Persona") @HasMany(associatedWith = "profesorID", type = Persona.class) List<Persona> Personas = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getNombre() {
      return Nombre;
  }
  
  public String getCargo() {
      return Cargo;
  }
  
  public List<Persona> getPersonas() {
      return Personas;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Profesor(String id, String Nombre, String Cargo) {
    this.id = id;
    this.Nombre = Nombre;
    this.Cargo = Cargo;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Profesor profesor = (Profesor) obj;
      return ObjectsCompat.equals(getId(), profesor.getId()) &&
              ObjectsCompat.equals(getNombre(), profesor.getNombre()) &&
              ObjectsCompat.equals(getCargo(), profesor.getCargo()) &&
              ObjectsCompat.equals(getCreatedAt(), profesor.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), profesor.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getNombre())
      .append(getCargo())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Profesor {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("Nombre=" + String.valueOf(getNombre()) + ", ")
      .append("Cargo=" + String.valueOf(getCargo()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Profesor justId(String id) {
    return new Profesor(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      Nombre,
      Cargo);
  }
  public interface BuildStep {
    Profesor build();
    BuildStep id(String id);
    BuildStep nombre(String nombre);
    BuildStep cargo(String cargo);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String Nombre;
    private String Cargo;
    public Builder() {
      
    }
    
    private Builder(String id, String Nombre, String Cargo) {
      this.id = id;
      this.Nombre = Nombre;
      this.Cargo = Cargo;
    }
    
    @Override
     public Profesor build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Profesor(
          id,
          Nombre,
          Cargo);
    }
    
    @Override
     public BuildStep nombre(String nombre) {
        this.Nombre = nombre;
        return this;
    }
    
    @Override
     public BuildStep cargo(String cargo) {
        this.Cargo = cargo;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String nombre, String cargo) {
      super(id, Nombre, Cargo);
      
    }
    
    @Override
     public CopyOfBuilder nombre(String nombre) {
      return (CopyOfBuilder) super.nombre(nombre);
    }
    
    @Override
     public CopyOfBuilder cargo(String cargo) {
      return (CopyOfBuilder) super.cargo(cargo);
    }
  }
  

  public static class ProfesorIdentifier extends ModelIdentifier<Profesor> {
    private static final long serialVersionUID = 1L;
    public ProfesorIdentifier(String id) {
      super(id);
    }
  }
  
}
