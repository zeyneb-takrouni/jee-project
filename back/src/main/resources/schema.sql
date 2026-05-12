-- Script d'initialisation de la base de données pour le système de gestion des achats

-- Création de la base de données
CREATE DATABASE IF NOT EXISTS purchasing_db;
USE purchasing_db;

-- Table des fournisseurs (Suppliers)
CREATE TABLE IF NOT EXISTS supplier (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    address VARCHAR(500),
    qualite_service INT CHECK (qualite_service >= 0 AND qualite_service <= 10),
    note INT CHECK (note >= 0 AND note <= 10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table des produits (Products)
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table des commandes d'achat (Purchase Orders)
CREATE TABLE IF NOT EXISTS purchase_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    order_date DATE NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED') DEFAULT 'PENDING',
    supplier_id BIGINT,
    total_amount DECIMAL(10,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES supplier(id) ON DELETE SET NULL
);

-- Table des lignes de commande d'achat (Purchase Order Lines)
CREATE TABLE IF NOT EXISTS purchase_order_line (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchase_order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantite INT NOT NULL CHECK (quantite > 0),
    prix_unitaire DECIMAL(10,2) NOT NULL CHECK (prix_unitaire > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (purchase_order_id) REFERENCES purchase_order(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- Table de l'historique des achats (Purchase History)
CREATE TABLE IF NOT EXISTS purchase_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantite INT NOT NULL CHECK (quantite > 0),
    delai_livraison INT, -- en jours
    prix_total DECIMAL(10,2),
    date_achat DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES supplier(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- Index pour améliorer les performances
CREATE INDEX idx_supplier_email ON supplier(contact_email);
CREATE INDEX idx_purchase_order_supplier ON purchase_order(supplier_id);
CREATE INDEX idx_purchase_order_status ON purchase_order(status);
CREATE INDEX idx_purchase_order_date ON purchase_order(order_date);
CREATE INDEX idx_purchase_order_line_order ON purchase_order_line(purchase_order_id);
CREATE INDEX idx_purchase_history_supplier ON purchase_history(supplier_id);
CREATE INDEX idx_purchase_history_product ON purchase_history(product_id);
CREATE INDEX idx_purchase_history_date ON purchase_history(date_achat);

-- Insertion de données de test
INSERT INTO supplier (name, contact_email, phone_number, address, qualite_service, note) VALUES
('TechCorp', 'contact@techcorp.com', '+33123456789', '123 Tech Street, Paris', 8, 9),
('SupplyPlus', 'info@supplyplus.fr', '+33234567890', '456 Supply Avenue, Lyon', 7, 8),
('GlobalTrade', 'sales@globaltrade.com', '+33345678901', '789 Trade Boulevard, Marseille', 9, 9);

INSERT INTO product (name, description, category) VALUES
('Ordinateur Portable', 'Ordinateur portable professionnel 15 pouces', 'Informatique'),
('Imprimante Laser', 'Imprimante laser couleur haute résolution', 'Bureautique'),
('Serveur', 'Serveur rack 2U pour entreprise', 'Informatique'),
('Clavier', 'Clavier mécanique sans fil', 'Accessoires'),
('Souris', 'Souris ergonomique optique', 'Accessoires');

INSERT INTO purchase_order (order_number, order_date, status, supplier_id, total_amount) VALUES
('PO-2024-001', '2024-01-15', 'COMPLETED', 1, 2500.00),
('PO-2024-002', '2024-01-20', 'APPROVED', 2, 1800.00),
('PO-2024-003', '2024-01-25', 'PENDING', 3, 3200.00);

INSERT INTO purchase_order_line (purchase_order_id, product_id, quantite, prix_unitaire) VALUES
(1, 1, 5, 500.00),
(1, 4, 10, 25.00),
(2, 2, 3, 300.00),
(2, 5, 15, 20.00),
(3, 3, 2, 1200.00),
(3, 1, 2, 500.00);

INSERT INTO purchase_history (supplier_id, product_id, quantite, delai_livraison, prix_total, date_achat) VALUES
(1, 1, 10, 5, 5000.00, '2024-01-10'),
(1, 4, 20, 3, 500.00, '2024-01-12'),
(2, 2, 5, 7, 1500.00, '2024-01-08'),
(2, 5, 25, 4, 500.00, '2024-01-14'),
(3, 3, 3, 10, 3600.00, '2024-01-05'),
(3, 1, 8, 6, 4000.00, '2024-01-18');