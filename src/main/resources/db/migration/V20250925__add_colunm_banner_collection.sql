-- ============================================
-- Migration Flyway : Banner, Collection, BannerImage, CollectionImage
-- Version : V20250925
-- ============================================

-- TABLE UTILISATEUR doit déjà exister avec clé primaire id_utilisateur
-- Vérifier avant migration

-- ============================================
-- TABLE BANNER
-- ============================================
CREATE TABLE IF NOT EXISTS banner (
    id_banner VARCHAR(100) PRIMARY KEY,
    sous_titre VARCHAR(255),
    titre VARCHAR(255),
    image_url VARCHAR(500),
    btn_text VARCHAR(255),
    btn_link VARCHAR(500),
    supprimer_status VARCHAR(50),
    publier BOOLEAN DEFAULT FALSE,
    utilisateur_banner_id BIGINT,
    CONSTRAINT fk_banner_utilisateur FOREIGN KEY (utilisateur_banner_id) REFERENCES utilisateur(id)
);

-- ============================================
-- TABLE COLLECTION
-- ============================================
CREATE TABLE IF NOT EXISTS collection (
    id_collection VARCHAR(100) PRIMARY KEY,
    sous_titre VARCHAR(255),
    titre VARCHAR(255),
    image_url VARCHAR(500),
    btn_text VARCHAR(255),
    btn_link VARCHAR(500),
    supprimer_status VARCHAR(50),
    publier BOOLEAN DEFAULT FALSE,
    utilisateur_collection_id BIGINT,
    CONSTRAINT fk_collection_utilisateur FOREIGN KEY (utilisateur_collection_id) REFERENCES utilisateur(id)
);

-- ============================================
-- TABLE BANNER_IMAGE
-- ============================================
CREATE TABLE IF NOT EXISTS banner_image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(100),
    data LONGBLOB,
    banner_id VARCHAR(100),
    CONSTRAINT fk_bannerimage_banner FOREIGN KEY (banner_id) REFERENCES banner(id_banner) ON DELETE CASCADE
);

-- ============================================
-- TABLE COLLECTION_IMAGE
-- ============================================
CREATE TABLE IF NOT EXISTS collection_image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(100),
    data LONGBLOB,
    collection_id VARCHAR(100),
    CONSTRAINT fk_collectionimage_collection FOREIGN KEY (collection_id) REFERENCES collection(id_collection) ON DELETE CASCADE
);
