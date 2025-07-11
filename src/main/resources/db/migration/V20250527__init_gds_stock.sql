-- ======================= UTILISATEUR =======================
CREATE TABLE utilisateur (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(60),
    prenom VARCHAR(60),
    telephone VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    date DATE,
    authentification VARCHAR(255),
    activation VARCHAR(255),
    supprimer_status VARCHAR(255)
);

-- ======================= USER_ROLE =======================
CREATE TABLE user_role (
    name VARCHAR(50) PRIMARY KEY
);

-- ======================= UTILISATEUR <-> USER_ROLE =======================
CREATE TABLE utilisateur_roles (
    utilisateur_id BIGINT NOT NULL,
    roles_name VARCHAR(50) NOT NULL,
    PRIMARY KEY(utilisateur_id, roles_name),
    FOREIGN KEY(utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY(roles_name) REFERENCES user_role(name) ON DELETE CASCADE
);

-- ======================= CATEGORIE_STOCK =======================
CREATE TABLE categorie_stock (
    id_cat BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(60),
    description VARCHAR(255),
    date DATE,
    publier BOOLEAN,
    slug VARCHAR(255) UNIQUE,
    supprimer_status VARCHAR(255),
    utilisateur_cat_id BIGINT,
    FOREIGN KEY(utilisateur_cat_id) REFERENCES utilisateur(id) ON DELETE SET NULL
);

-- ======================= CLIENT =======================
CREATE TABLE client (
    id_client VARCHAR(256) PRIMARY KEY,
    nom VARCHAR(60),
    prenom VARCHAR(60),
    adresse VARCHAR(256),
    telephone VARCHAR(15),
    email VARCHAR(80),
    date_ajout DATE,
    publier BOOLEAN,
    supprimer_status VARCHAR(255),
    utilisateur_client_id BIGINT,
    FOREIGN KEY(utilisateur_client_id) REFERENCES utilisateur(id) ON DELETE SET NULL
);

-- ======================= PRODUIT =======================
CREATE TABLE produit (
    id_prod VARCHAR(256) PRIMARY KEY,
    designation VARCHAR(256),
    quantite INT,
    prix_unitaire INT,
    montant INT,
    date DATE,
    note VARCHAR(255),
    supprimer_status VARCHAR(255),
    slug VARCHAR(255) UNIQUE,
    prix_regulier VARCHAR(255),
    description VARCHAR(2560),
    info VARCHAR(2560),
    caracteristique VARCHAR(256),
    nouveaute BOOLEAN,
    vedette BOOLEAN,
    offre_speciale BOOLEAN,
    plus_vendu BOOLEAN,
    publier BOOLEAN,
    utilisateur_prod_id BIGINT,
    categorie_stock_id_cat BIGINT,
    FOREIGN KEY (utilisateur_prod_id) REFERENCES utilisateur(id),
    FOREIGN KEY (categorie_stock_id_cat) REFERENCES categorie_stock(id_cat)
);

-- ======================= PRODUIT_IMAGE =======================
CREATE TABLE produit_image (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    type VARCHAR(256),
    data LONGBLOB,
    produit_id VARCHAR(256),
    FOREIGN KEY(produit_id) REFERENCES produit(id_prod) ON DELETE CASCADE
);

-- ======================= APPROVISION =======================
CREATE TABLE approvision (
    id_approv VARCHAR(256) PRIMARY KEY,
    designation VARCHAR(256),
    quantite INT,
    prix_unitaire INT,
    montant INT,
    cbm DOUBLE,
    frais_transit INT,
    date_achat DATE,
    date_arriver DATE,
    adresse_frs VARCHAR(255),
    image VARCHAR(255),
    etat VARCHAR(256),
    description VARCHAR(255),
    supprimer_status VARCHAR(255),
    produits_approv_id VARCHAR(256),
    utilisateur_aprov_id BIGINT,
    FOREIGN KEY(produits_approv_id) REFERENCES produit(id_prod) ON DELETE SET NULL,
    FOREIGN KEY(utilisateur_aprov_id) REFERENCES utilisateur(id) ON DELETE SET NULL
);

-- ======================= DETTE =======================
CREATE TABLE dette (
    id_dette VARCHAR(256) PRIMARY KEY,
    titre VARCHAR(256),
    quantite INT,
    reduction INT,
    montant INT,
    date_debut DATE,
    date_fin DATE,
    status VARCHAR(256),
    note VARCHAR(255),
    supprimer_status VARCHAR(255),
    utilisateur_dette_id BIGINT,
    client_dette_id VARCHAR(256),
    FOREIGN KEY(utilisateur_dette_id) REFERENCES utilisateur(id) ON DELETE SET NULL,
    FOREIGN KEY(client_dette_id) REFERENCES client(id_client) ON DELETE SET NULL
);

-- ======================= DETTE_PRODUIT =======================
CREATE TABLE dette_produit (
    id_dette_produit BIGINT PRIMARY KEY AUTO_INCREMENT,
    montant INT,
    quantite INT,
    reduction INT,
    produit_id VARCHAR(256),
    dette_id VARCHAR(256),
    FOREIGN KEY(produit_id) REFERENCES produit(id_prod) ON DELETE CASCADE,
    FOREIGN KEY(dette_id) REFERENCES dette(id_dette) ON DELETE CASCADE
);

-- ======================= VENTE =======================
CREATE TABLE vente (
    id_vente VARCHAR(256) PRIMARY KEY,
    quantite INT,
    montant INT,
    reduction INT,
    note VARCHAR(255),
    date_vente DATE,
    status VARCHAR(256),
    supprimer_status VARCHAR(255),
    utilisateur_vente_id BIGINT,
    clients_vente_id_client VARCHAR(256),
    FOREIGN KEY(utilisateur_vente_id) REFERENCES utilisateur(id) ON DELETE SET NULL,
    FOREIGN KEY(clients_vente_id_client) REFERENCES client(id_client) ON DELETE SET NULL
);

-- ======================= VENTE_PRODUIT =======================
CREATE TABLE vente_produit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    montant INT,
    quantite INT,
    reduction INT,
    produit_id_prod VARCHAR(255),
    vente_id_vente VARCHAR(255),
    FOREIGN KEY (produit_id_prod) REFERENCES produit(id_prod),
    FOREIGN KEY (vente_id_vente) REFERENCES vente(id_vente)
);

-- ======================= COMMANDE =======================
CREATE TABLE commande (
    id_cde VARCHAR(255) PRIMARY KEY,
    quantite INT,
    total INT,
    supprimer_status VARCHAR(255),
    date_ajout DATE,
    utilisateur_cde_id BIGINT,
    client_cde_id VARCHAR(256),
    FOREIGN KEY (client_cde_id) REFERENCES client(id_client)
);


-- ======================= COMMANDE_PRODUIT =======================
CREATE TABLE commande_produit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    montant INT,
    quantite INT,
    produit_id VARCHAR(256),
    commande_id VARCHAR(255),
    FOREIGN KEY (produit_id) REFERENCES produit(id_prod),
    FOREIGN KEY (commande_id) REFERENCES commande(id_cde)
);

