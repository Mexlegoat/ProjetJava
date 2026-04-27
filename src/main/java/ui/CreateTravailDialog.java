package ui;

import java.awt.*;

public class CreateTravailDialog extends CreateItemDialog
{
    public CreateTravailDialog(Frame owner)
    {
        super(owner, "Travail", "Langage");
    }

    /*public static void main(String[] args)
    {
        CreateTravailDialog dlg = new CreateTravailDialog(null);
        dlg.setVisible(true);
        System.out.println("Saisie nom: " + dlg.nomField.getText());
        System.out.println("Saisie langage: " + dlg.extraField.getText());
    }*/
}