package com.amplifyframework.datastore.generated.model;

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

/** This is an auto generated class representing the Persona type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Personas", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byProfesor", fields = {"profesorID"})
public final class Persona implements Model {
  public static final QueryField ID = field("Persona", "id");
  public static final QueryField NOMBRE = field("Persona", "Nombre");
  public static final QueryField EDAD = field("Persona", "Edad");
  public static final QueryField PARTE = field("Persona", "Parte");
  public static final QueryField PROFESOR_ID = field("Persona", "profesorID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String Nombre;
  private final @ModelField(targetType="Int") Integer Edad;
  private final @ModelField(targetType="Boolean") Boolean Parte;
  private final @ModelField(targetType="ID", isRequired = true) String profesorID;
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
  
  public Integer getEdad() {
      return Edad;
  }
  
  public Boolean getParte() {
      return Parte;
  }
  
  public String getProfesorId() {
      return profesorID;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Persona(String id, String Nombre, Integer Edad, Boolean Parte, String profesorID) {
    this.id = id;
    this.Nombre = Nombre;
    this.Edad = Edad;
    this.Parte = Parte;
    this.profesorID = profesorID;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Persona persona = (Persona) obj;
      return ObjectsCompat.equals(getId(), persona.getId()) &&
              ObjectsCompat.equals(getNombre(), persona.getNombre()) &&
              ObjectsCompat.equals(getEdad(), persona.getEdad()) &&
              ObjectsCompat.equals(getParte(), persona.getParte()) &&
              ObjectsCompat.equals(getProfesorId(), persona.getProfesorId()) &&
              ObjectsCompat.equals(getCreatedAt(), persona.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), persona.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getNombre())
      .append(getEdad())
      .append(getParte())
      .append(getProfesorId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Persona {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("Nombre=" + String.valueOf(getNombre()) + ", ")
      .append("Edad=" + String.valueOf(getEdad()) + ", ")
      .append("Parte=" + String.valueOf(getParte()) + ", ")
      .append("profesorID=" + String.valueOf(getProfesorId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ProfesorIdStep builder() {
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
  public static Persona justId(String id) {
    return new Persona(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      Nombre,
      Edad,
      Parte,
      profesorID);
  }
  public interface ProfesorIdStep {
    BuildStep profesorId(String profesorId);
  }
  

  public interface BuildStep {
    Persona build();
    BuildStep id(String id);
    BuildStep nombre(String nombre);
    BuildStep edad(Integer edad);
    BuildStep parte(Boolean parte);
  }
  

  public static class Builder implements ProfesorIdStep, BuildStep {
    private String id;
    private String profesorID;
    private String Nombre;
    private Integer Edad;
    private Boolean Parte;
    public Builder() {
      
    }
    
    private Builder(String id, String Nombre, Integer Edad, Boolean Parte, String profesorID) {
      this.id = id;
      this.Nombre = Nombre;
      this.Edad = Edad;
      this.Parte = Parte;
      this.profesorID = profesorID;
    }
    
    @Override
     public Persona build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Persona(
          id,
          Nombre,
          Edad,
          Parte,
          profesorID);
    }
    
    @Override
     public BuildStep profesorId(String profesorId) {
        Objects.requireNonNull(profesorId);
        this.profesorID = profesorId;
        return this;
    }
    
    @Override
     public BuildStep nombre(String nombre) {
        this.Nombre = nombre;
        return this;
    }
    
    @Override
     public BuildStep edad(Integer edad) {
        this.Edad = edad;
        return this;
    }
    
    @Override
     public BuildStep parte(Boolean parte) {
        this.Parte = parte;
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
    private CopyOfBuilder(String id, String nombre, Integer edad, Boolean parte, String profesorId) {
      super(id, Nombre, Edad, Parte, profesorID);
      Objects.requireNonNull(profesorID);
    }
    
    @Override
     public CopyOfBuilder profesorId(String profesorId) {
      return (CopyOfBuilder) super.profesorId(profesorId);
    }
    
    @Override
     public CopyOfBuilder nombre(String nombre) {
      return (CopyOfBuilder) super.nombre(nombre);
    }
    
    @Override
     public CopyOfBuilder edad(Integer edad) {
      return (CopyOfBuilder) super.edad(edad);
    }
    
    @Override
     public CopyOfBuilder parte(Boolean parte) {
      return (CopyOfBuilder) super.parte(parte);
    }
  }
  

  public static class PersonaIdentifier extends ModelIdentifier<Persona> {
    private static final long serialVersionUID = 1L;
    public PersonaIdentifier(String id) {
      super(id);
    }
  }
  
}
