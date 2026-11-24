ALTER TABLE materiais
ADD COLUMN preco_unitario_dolar DECIMAL(10,2)
NOT NULL DEFAULT 0.00;

ALTER TABLE materiais
ADD CONSTRAINT chk_preco_material
CHECK (preco_unitario_dolar >= 0);