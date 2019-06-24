package tacos.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    /*
    A Taco can have many Ingredient objects, and an Ingredient can be a part of many Tacos.
     */
    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    private Date createdAt;

    /*
    You’ll use this to set the createdAt property to the current date and time before Taco is persisted.
     */
    @PrePersist
    void createdAt(){
        this.createdAt=new Date();
    }

}
