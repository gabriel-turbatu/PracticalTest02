package ro.cs.pub.systems.eim.practicaltest02;

public class PokemonInformation {
    private String image;
    private String abilities;
    private String types;
    private String name;

    public PokemonInformation() {
        this.image = null;
        this.abilities = null;
        this.types = null;
        this.name = null;
    }

    public PokemonInformation(String image, String abilities, String types, String name) {
        this.image = image;
        this.abilities = abilities;
        this.types = types;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getTypes() {
        return types;
    }

    @Override
    public String toString() {
        String result = "PokemonInformation{name='" + name + "', abilities=[";

        result += abilities + "], image=[";
        result += image + "], types=[";
        result += types + "]}";

        return result;
    }
}
