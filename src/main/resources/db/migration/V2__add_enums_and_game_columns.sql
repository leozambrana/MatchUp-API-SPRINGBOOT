-- V2__add_enums_and_game_columns.sql

CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TYPE court_type AS ENUM ('SAND', 'GRASS', 'SOCIETY', 'INDOOR', 'OTHER');
CREATE TYPE match_format AS ENUM ('1X1', '2X2', '3X3', '4X4', '5X5', '6X6', '7X7', '11X11', 'OTHER');
CREATE TYPE match_gender AS ENUM ('MALE', 'FEMALE', 'MIXED');
CREATE TYPE match_duration AS ENUM ('MIN_30', 'MIN_45', 'HOUR_1', 'HOUR_1_30', 'HOUR_2', 'HOUR_2_30', 'HOUR_3');
CREATE TYPE recurrence_type AS ENUM ('UNIQUE', 'WEEKLY', 'MONTHLY');
CREATE TYPE participant_status AS ENUM ('PENDING', 'CONFIRMED', 'DECLINED', 'CANCELLED');

ALTER TABLE games
    ADD COLUMN court_type   court_type,
    ADD COLUMN match_format match_format,
    ADD COLUMN gender       match_gender,
    ADD COLUMN slots        INTEGER,
    ADD COLUMN duration     match_duration,
    ADD COLUMN recurrence   recurrence_type,
    ADD COLUMN invite_code  VARCHAR(8) UNIQUE;

ALTER TABLE participants
    ALTER COLUMN status TYPE participant_status
    USING status::participant_status;

CREATE TRIGGER trg_set_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_set_updated_at
    BEFORE UPDATE ON games
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_set_updated_at
    BEFORE UPDATE ON participants
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_set_updated_at
    BEFORE UPDATE ON expenses
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_set_updated_at
    BEFORE UPDATE ON expense_splits
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();