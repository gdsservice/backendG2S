-- Ajout de la colonne «traiter» dans la table commande
ALTER TABLE commande
    ADD COLUMN traiter BOOLEAN NOT NULL DEFAULT FALSE
    AFTER total;