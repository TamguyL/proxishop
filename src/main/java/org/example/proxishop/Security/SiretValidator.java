package org.example.proxishop.Security;

public class SiretValidator {
    /**
     * Vérifie si un numéro SIRET est valide en utilisant l'algorithme de Luhn.
     *
     * @param siret Le numéro SIRET à valider.
     * @return true si le numéro SIRET est valide, false sinon.
     */
    public static boolean isValidSiret(String siret) {
        if (siret == null || siret.length() != 14 || !siret.matches("\\d+")) {
            return false;
        }
        int sum = 0;
        boolean alternate = false;

        for (int i = siret.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(siret.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }
}
