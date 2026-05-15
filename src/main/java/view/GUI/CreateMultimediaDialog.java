package view.GUI;

import java.awt.*;

public class CreateMultimediaDialog extends CreateItemDialog
{
    public CreateMultimediaDialog(Frame owner) {
        super(owner, "Multimedia", "Type");
    }

    /*public static void main(String[] args)
    {
        CreateMultimediaDialog dlg = new CreateMultimediaDialog(null);
        dlg.setVisible(true);
        System.out.println("Saisie nom: " + dlg.nomField.getText());
        System.out.println("Saisie genre: " + dlg.extraField.getText());
    }*/
}
