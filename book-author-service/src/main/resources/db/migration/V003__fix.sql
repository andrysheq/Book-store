ALTER TABLE "order" ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

UPDATE "order"
SET created_at = NOW() - (RANDOM() * INTERVAL '1 year')
WHERE id>0;

CREATE INDEX idx_order_user_created ON "order" (user_id, created_at DESC);
ALTER TABLE "user" ADD COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

UPDATE "user"
SET created_date = NOW() - (RANDOM() * INTERVAL '1 year')
WHERE id>0;

CREATE INDEX idx_user_created ON "user" (created_date DESC);
ALTER TABLE review ADD COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

UPDATE review
SET created_date = NOW() - (RANDOM() * INTERVAL '1 year')
WHERE id>0;

CREATE INDEX idx_review_created ON "review" (created_date DESC);