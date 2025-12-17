ALTER TABLE review
    ADD COLUMN status_update_at TIMESTAMP;

CREATE OR REPLACE FUNCTION update_review_status_update_at()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.review_status_id IS DISTINCT FROM NEW.review_status_id THEN
        NEW.status_update_at = CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_review_status_update_at
    BEFORE UPDATE ON review
    FOR EACH ROW
EXECUTE FUNCTION update_review_status_update_at();


CREATE OR REPLACE FUNCTION reset_review_status_on_content_update()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.content IS DISTINCT FROM NEW.content
        OR OLD.rating IS DISTINCT FROM NEW.rating THEN
        NEW.review_status_id = 1;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_reset_review_status_on_content_update
    BEFORE UPDATE ON review
    FOR EACH ROW
EXECUTE FUNCTION reset_review_status_on_content_update();
