--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.26
-- Dumped by pg_dump version 9.4.26
-- Started on 2023-05-04 15:43:27

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 174 (class 1259 OID 150998)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.cliente (
    id_cliente integer NOT NULL,
    direccion character varying(100) NOT NULL,
    edad integer NOT NULL,
    genero character varying(1) NOT NULL,
    identificacion character varying(10) NOT NULL,
    nombre character varying(100) NOT NULL,
    telefono character varying(10) NOT NULL,
    contrasenia character varying(255) NOT NULL,
    estado boolean NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 150996)
-- Name: cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cliente_id_seq OWNER TO postgres;

--
-- TOC entry 2026 (class 0 OID 0)
-- Dependencies: 173
-- Name: cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_id_seq OWNED BY public.cliente.id_cliente;


--
-- TOC entry 176 (class 1259 OID 151006)
-- Name: cuenta; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.cuenta (
    id_cuenta bigint NOT NULL,
    estado boolean NOT NULL,
    fk_cliente integer NOT NULL,
    numero_cuenta character varying(255) NOT NULL,
    saldo_inicial numeric(19,2) NOT NULL,
    tipo_cuenta character varying(1) NOT NULL
);


ALTER TABLE public.cuenta OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 151004)
-- Name: cuenta_id_cuenta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cuenta_id_cuenta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cuenta_id_cuenta_seq OWNER TO postgres;

--
-- TOC entry 2027 (class 0 OID 0)
-- Dependencies: 175
-- Name: cuenta_id_cuenta_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuenta_id_cuenta_seq OWNED BY public.cuenta.id_cuenta;


--
-- TOC entry 178 (class 1259 OID 151014)
-- Name: movimiento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.movimiento (
    id_movimiento bigint NOT NULL,
    fecha timestamp without time zone NOT NULL,
    fk_cuenta bigint NOT NULL,
    saldo numeric(19,2) NOT NULL,
    tipo_movimiento character varying(1) NOT NULL,
    valor numeric(19,2) NOT NULL
);


ALTER TABLE public.movimiento OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 151012)
-- Name: movimiento_id_movimientos_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movimiento_id_movimientos_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movimiento_id_movimientos_seq OWNER TO postgres;

--
-- TOC entry 2028 (class 0 OID 0)
-- Dependencies: 177
-- Name: movimiento_id_movimientos_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movimiento_id_movimientos_seq OWNED BY public.movimiento.id_movimiento;


--
-- TOC entry 1893 (class 2604 OID 151001)
-- Name: id_cliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN id_cliente SET DEFAULT nextval('public.cliente_id_seq'::regclass);


--
-- TOC entry 1894 (class 2604 OID 151009)
-- Name: id_cuenta; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta ALTER COLUMN id_cuenta SET DEFAULT nextval('public.cuenta_id_cuenta_seq'::regclass);


--
-- TOC entry 1895 (class 2604 OID 151017)
-- Name: id_movimiento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento ALTER COLUMN id_movimiento SET DEFAULT nextval('public.movimiento_id_movimientos_seq'::regclass);


--
-- TOC entry 2014 (class 0 OID 150998)
-- Dependencies: 174
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (id_cliente, direccion, edad, genero, identificacion, nombre, telefono, contrasenia, estado) FROM stdin;
2	Amazonas y  NNUU	35	F	0704907922	Marianela Montalvo	9999999999	5678	t
3	13 junio y Equinoccial	41	M	0100561430	Juan Osorio	0908874587	1245	t
1	Otavalo sn y principal	38	F	0704907921	Jose Lema	0990593431	1234	t
\.


--
-- TOC entry 2029 (class 0 OID 0)
-- Dependencies: 173
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_id_seq', 10, true);


--
-- TOC entry 2016 (class 0 OID 151006)
-- Dependencies: 176
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cuenta (id_cuenta, estado, fk_cliente, numero_cuenta, saldo_inicial, tipo_cuenta) FROM stdin;
1	t	2	225487	2000.00	A
3	t	3	495878	0.00	A
2	t	1	478758	2000.00	A
4	t	3	496825	100.00	A
15	t	3	9898989	2000.00	A
\.


--
-- TOC entry 2030 (class 0 OID 0)
-- Dependencies: 175
-- Name: cuenta_id_cuenta_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuenta_id_cuenta_seq', 15, true);


--
-- TOC entry 2018 (class 0 OID 151014)
-- Dependencies: 178
-- Data for Name: movimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.movimiento (id_movimiento, fecha, fk_cuenta, saldo, tipo_movimiento, valor) FROM stdin;
30	2023-05-02 00:00:00	2	1425.00	R	-575.00
31	2023-05-03 00:00:00	2	1675.00	D	250.00
33	2023-05-03 00:00:00	2	1575.00	R	-100.00
34	2023-05-03 00:00:00	4	0.00	R	-100.00
35	2023-05-03 00:00:00	2	2575.00	D	1000.00
36	2023-05-04 00:00:00	2	3575.00	D	1000.00
41	2023-05-03 00:00:00	2	3475.00	R	-100.00
42	2023-05-03 00:00:00	2	2675.00	R	-800.00
\.


--
-- TOC entry 2031 (class 0 OID 0)
-- Dependencies: 177
-- Name: movimiento_id_movimientos_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.movimiento_id_movimientos_seq', 42, true);


--
-- TOC entry 1897 (class 2606 OID 151003)
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);


--
-- TOC entry 1899 (class 2606 OID 151011)
-- Name: cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (id_cuenta);


--
-- TOC entry 1901 (class 2606 OID 151019)
-- Name: movimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT movimiento_pkey PRIMARY KEY (id_movimiento);


--
-- TOC entry 1903 (class 2606 OID 151025)
-- Name: fk8veysyanipny5mpudj13t8873; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT fk8veysyanipny5mpudj13t8873 FOREIGN KEY (fk_cuenta) REFERENCES public.cuenta(id_cuenta);


--
-- TOC entry 1902 (class 2606 OID 151020)
-- Name: fk_cuenta_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT fk_cuenta_cliente FOREIGN KEY (fk_cliente) REFERENCES public.cliente(id_cliente);


--
-- TOC entry 2025 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-05-04 15:43:27

--
-- PostgreSQL database dump complete
--

