-- Ajout de la colonne «traiter» dans la table commande
ALTER TABLE commande
    ADD COLUMN note VARCHAR(2560)
    AFTER total;