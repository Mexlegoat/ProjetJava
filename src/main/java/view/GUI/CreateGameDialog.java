package view.GUI;

import java.awt.Frame;

public class CreateGameDialog extends CreateItemDialog
{
    public CreateGameDialog(Frame owner)
    {
        super(owner, "Jeu", "Genre");
    }

    /*
    public static void main(String[] args)
    {
        CreateGameDialog dlg = new CreateGameDialog(null);
        dlg.setVisible(true);
        System.out.println("Saisie nom: " + dlg.nomField.getText());
        System.out.println("Saisie genre: " + dlg.extraField.getText());
    }*/
}
