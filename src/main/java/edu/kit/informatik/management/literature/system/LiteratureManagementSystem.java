package edu.kit.informatik.management.literature.system;

import edu.kit.informatik.management.literature.LiteratureManagement;

/**
 * @author David Oberacker
 */
public class LiteratureManagementSystem {

    LiteratureManagement literatureManagement;

    public LiteratureManagementSystem() {
        this.literatureManagement = new LiteratureManagement();
    }

    public static void main(String[] args) {
        LiteratureManagementSystem lms = new LiteratureManagementSystem();
    }


}
