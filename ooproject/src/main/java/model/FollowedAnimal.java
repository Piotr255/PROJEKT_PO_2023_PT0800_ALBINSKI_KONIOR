package model;

public class FollowedAnimal {

    private static Animal followedAnimal;

    public static Animal getFollowedAnimal(){
        return followedAnimal;
    }

    public static void setFollowedAnimal(Animal animal){
        followedAnimal = animal;
    }

}
