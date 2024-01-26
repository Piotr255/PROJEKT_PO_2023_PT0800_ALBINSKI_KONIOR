package model;

public class FollowedAnimal { // co wnosi ta klasa?

    private static Animal followedAnimal; // czy na pewno static?

    public static Animal getFollowedAnimal(){
        return followedAnimal;
    }

    public static void setFollowedAnimal(Animal animal){
        followedAnimal = animal;
    }

}
