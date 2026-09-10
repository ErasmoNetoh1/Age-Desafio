CREATE TABLE funcionario (rowid bigint auto_increment PRIMARY KEY, nm_funcionario VARCHAR(255));
INSERT INTO funcionario (nm_funcionario) VALUES ('João'), ('Maria'), ('José'), ('Joana');

CREATE TABLE agenda(
	rowid bigint auto_increment PRIMARY KEY,
	nm_agenda VARCHAR(255) NOT NULL,
	tp_periodo_disponivel VARCHAR(10) NOT NULL
);

CREATE TABLE compromisso (
	rowid bigint auto_increment PRIMARY KEY,
	id_funcionario bigint NOT NULL,
	id_agenda bigint NOT NULL,
    dt_compromisso DATE NOT NULL,
    hr_compromisso TIME NOT NULL,
    CONSTRAINT fk_compromisso_funcionario
        FOREIGN KEY (id_funcionario) REFERENCES funcionario(rowid),
    CONSTRAINT fk_compromisso_agenda
        FOREIGN KEY (id_agenda) REFERENCES agenda(rowid)
);
