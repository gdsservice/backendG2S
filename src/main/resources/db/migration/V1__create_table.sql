
CREATE TABLE IF NOT EXISTS utilisateur (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(60),
    prenom VARCHAR(60),
    telephone VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(80) NOT NULL,
    date DATE,
    authentification VARCHAR(255),
    activation VARCHAR(255),
    supprimer_status VARCHAR(255)
);

CREATE TABLE user_role (
    name VARCHAR(255) PRIMARY KEY NOT NULL
);

CREATE TABLE utilisateur_roles (
    utilisateur_id BIGINT,
    roles_name VARCHAR(255),
    PRIMARY KEY (utilisateur_id, roles_name),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id),
    FOREIGN KEY (roles_name) REFERENCES user_role(name)
);

CREATE TABLE categorie_stock (
    id_cat BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(60),
    description VARCHAR(100),
    date DATE,
    supprimer_status VARCHAR(255),
    utilisateur_cat_id BIGINT,
    FOREIGN KEY (utilisateur_cat_id) REFERENCES utilisateur(id)
);

CREATE TABLE client (
    id_client VARCHAR(255) PRIMARY KEY,
    nom VARCHAR(60),
    prenom VARCHAR(60),
    adresse VARCHAR(80),
    telephone VARCHAR(15),
    email VARCHAR(80),
    date_ajout DATE,
    supprimer_status VARCHAR(255),
    utilisateur_client_id BIGINT,
    FOREIGN KEY (utilisateur_client_id) REFERENCES utilisateur(id)
);

CREATE TABLE produit (
    id_prod VARCHAR(255) PRIMARY KEY,
    designation VARCHAR(60),
    quantite INT,
    prix_unitaire INT,
    montant INT,
    date DATE,
    note VARCHAR(255),
    supprimer_status VARCHAR(255),
    image_data LONGBLOB,
    image_type VARCHAR(255),
    image_name VARCHAR(255),
    utilisateur_prod_id BIGINT,
    categorie_stock_id_cat BIGINT,
    FOREIGN KEY (utilisateur_prod_id) REFERENCES utilisateur(id),
    FOREIGN KEY (categorie_stock_id_cat) REFERENCES categorie_stock(id_cat)
);

CREATE TABLE approvision (
    id_approv VARCHAR(255) PRIMARY KEY,
    designation VARCHAR(60),
    quantite INT,
    prix_unitaire INT,
    montant INT,
    cbm DOUBLE,
    frais_transit INT,
    date_achat DATE,
    date_arriver DATE,
    adresse_frs VARCHAR(60),
    image VARCHAR(255),
    etat VARCHAR(255),
    description VARCHAR(100),
    supprimer_status VARCHAR(255),
    produits_approv_id_prod VARCHAR(255),
    utilisateur_aprov_id BIGINT,
    FOREIGN KEY (produits_approv_id_prod) REFERENCES produit(id_prod),
    FOREIGN KEY (utilisateur_aprov_id) REFERENCES utilisateur(id)
);

CREATE TABLE dette (
    id_dette VARCHAR(255) PRIMARY KEY,
    titre VARCHAR(50),
    quantite INT,
    reduction INT,
    montant INT,
    date_debut DATE,
    date_fin DATE,
    status VARCHAR(255),
    note VARCHAR(100),
    supprimer_status VARCHAR(255),
    utilisateur_dette_id BIGINT,
    client_dette_id_client VARCHAR(255),
    FOREIGN KEY (utilisateur_dette_id) REFERENCES utilisateur(id),
    FOREIGN KEY (client_dette_id_client) REFERENCES client(id_client)
);

CREATE TABLE dette_produit (
    id_dette_produit BIGINT AUTO_INCREMENT PRIMARY KEY,
    montant INT,
    quantite INT,
    reduction INT,
    produit_id_prod VARCHAR(255),
    dette_id_dette VARCHAR(255),
    FOREIGN KEY (produit_id_prod) REFERENCES produit(id_prod),
    FOREIGN KEY (dette_id_dette) REFERENCES dette(id_dette)
);

CREATE TABLE vente (
    id_vente VARCHAR(255) PRIMARY KEY,
    quantite INT,
    montant INT,
    reduction INT,
    note VARCHAR(100),
    date_vente DATE,
    status VARCHAR(255),
    supprimer_status VARCHAR(255),
    utilisateur_vente_id BIGINT,
    clients_vente_id_client VARCHAR(255),
    FOREIGN KEY (utilisateur_vente_id) REFERENCES utilisateur(id),
    FOREIGN KEY (clients_vente_id_client) REFERENCES client(id_client)
);

CREATE TABLE vente_produit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    montant INT,
    quantite INT,
    reduction INT,
    produit_id_prod VARCHAR(255),
    vente_id_vente VARCHAR(255),
    FOREIGN KEY (produit_id_prod) REFERENCES produit(id_prod),
    FOREIGN KEY (vente_id_vente) REFERENCES vente(id_vente)
);


