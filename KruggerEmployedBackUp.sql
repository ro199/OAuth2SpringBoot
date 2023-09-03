--
-- PostgreSQL database dump
--

-- Dumped from database version 16rc1
-- Dumped by pg_dump version 16rc1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    id integer NOT NULL,
    client_id character varying(255),
    client_secret character varying(255),
    require_proof_key boolean NOT NULL,
    scopes character varying(255)[]
);


ALTER TABLE public.client OWNER TO postgres;

--
-- Name: client_authentication_methods; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_authentication_methods (
    client_id integer NOT NULL,
    authentication_methods bytea
);


ALTER TABLE public.client_authentication_methods OWNER TO postgres;

--
-- Name: client_authorization_grant_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_authorization_grant_types (
    client_id integer NOT NULL,
    authorization_grant_types bytea
);


ALTER TABLE public.client_authorization_grant_types OWNER TO postgres;

--
-- Name: client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.client_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.client_id_seq OWNER TO postgres;

--
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;


--
-- Name: client_redirect_uris; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client_redirect_uris (
    client_id integer NOT NULL,
    redirect_uris character varying(255)
);


ALTER TABLE public.client_redirect_uris OWNER TO postgres;

--
-- Name: empleado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empleado (
    id bigint NOT NULL,
    apellidos character varying(255),
    cedula character varying(255),
    correo character varying(255),
    direccion_domicilio character varying(255),
    fecha_nacimiento timestamp(6) without time zone,
    nombres character varying(255),
    telefono_movil character varying(255),
    vacunado boolean NOT NULL
);


ALTER TABLE public.empleado OWNER TO postgres;

--
-- Name: empleado_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empleado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.empleado_id_seq OWNER TO postgres;

--
-- Name: empleado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.empleado_id_seq OWNED BY public.empleado.id;


--
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id integer NOT NULL,
    role character varying(255),
    CONSTRAINT role_role_check CHECK (((role)::text = ANY ((ARRAY['ROLE_ADMIN'::character varying, 'ROLE_USER'::character varying])::text[])))
);


ALTER TABLE public.role OWNER TO postgres;

--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.role_id_seq OWNER TO postgres;

--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_role OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    credentials_expired boolean NOT NULL,
    disabled boolean NOT NULL,
    expired boolean NOT NULL,
    locked boolean NOT NULL,
    password character varying(255),
    username character varying(255),
    empleado_id bigint
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: vacuna; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vacuna (
    id bigint NOT NULL,
    fecha_vacunacion timestamp(6) without time zone,
    numero_dosis integer NOT NULL,
    tipo_vacuna character varying(255),
    empleado_id bigint NOT NULL
);


ALTER TABLE public.vacuna OWNER TO postgres;

--
-- Name: vacuna_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vacuna_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vacuna_id_seq OWNER TO postgres;

--
-- Name: vacuna_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vacuna_id_seq OWNED BY public.vacuna.id;


--
-- Name: client id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client ALTER COLUMN id SET DEFAULT nextval('public.client_id_seq'::regclass);


--
-- Name: empleado id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empleado ALTER COLUMN id SET DEFAULT nextval('public.empleado_id_seq'::regclass);


--
-- Name: role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: vacuna id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacuna ALTER COLUMN id SET DEFAULT nextval('public.vacuna_id_seq'::regclass);


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client (id, client_id, client_secret, require_proof_key, scopes) FROM stdin;
4	client	$2a$10$d2WTGry9BGrwBePY2xeAbOqnztJWwYb5TUvlLHQqtFhuVFu2Ptlxy	t	{openid}
\.


--
-- Data for Name: client_authentication_methods; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_authentication_methods (client_id, authentication_methods) FROM stdin;
4	\\xaced0005737200436f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e636f72652e436c69656e7441757468656e7469636174696f6e4d6574686f6400000000000002620200014c000576616c75657400124c6a6176612f6c616e672f537472696e673b7870740013636c69656e745f7365637265745f6261736963
\.


--
-- Data for Name: client_authorization_grant_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_authorization_grant_types (client_id, authorization_grant_types) FROM stdin;
4	\\xaced00057372003f6f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e636f72652e417574686f72697a6174696f6e4772616e745479706500000000000002620200014c000576616c75657400124c6a6176612f6c616e672f537472696e673b787074000d726566726573685f746f6b656e
4	\\xaced00057372003f6f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e636f72652e417574686f72697a6174696f6e4772616e745479706500000000000002620200014c000576616c75657400124c6a6176612f6c616e672f537472696e673b7870740012636c69656e745f63726564656e7469616c73
4	\\xaced00057372003f6f72672e737072696e676672616d65776f726b2e73656375726974792e6f61757468322e636f72652e417574686f72697a6174696f6e4772616e745479706500000000000002620200014c000576616c75657400124c6a6176612f6c616e672f537472696e673b7870740012617574686f72697a6174696f6e5f636f6465
\.


--
-- Data for Name: client_redirect_uris; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client_redirect_uris (client_id, redirect_uris) FROM stdin;
4	https://oauthdebugger.com/debug
\.


--
-- Data for Name: empleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.empleado (id, apellidos, cedula, correo, direccion_domicilio, fecha_nacimiento, nombres, telefono_movil, vacunado) FROM stdin;
1	ALVARADO	1726663981	ro199_fr@hotmail.com	asdqwe	\N	RONALD	0979390222	t
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (id, role) FROM stdin;
1	ROLE_ADMIN
2	ROLE_USER
\.


--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_role (user_id, role_id) FROM stdin;
4	1
4	2
5	2
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, credentials_expired, disabled, expired, locked, password, username, empleado_id) FROM stdin;
4	f	f	f	f	$2a$10$aHqMFMbNiOV7wtfslCNPkOTVLNWW53g/PF5xCiG44TWVc7u8KMXje	admin	\N
5	f	f	f	f	$2a$10$ohNHrPinUOhQArB2wcCms.7iPT.5zNfX0CaMbuyf.eyBpeDhKLacq	user	\N
\.


--
-- Data for Name: vacuna; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vacuna (id, fecha_vacunacion, numero_dosis, tipo_vacuna, empleado_id) FROM stdin;
\.


--
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_id_seq', 4, true);


--
-- Name: empleado_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.empleado_id_seq', 1, false);


--
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_id_seq', 2, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 5, true);


--
-- Name: vacuna_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vacuna_id_seq', 1, false);


--
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- Name: empleado empleado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT empleado_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: users uk_1x3i83bs11ily5fbktkmxkfg9; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_1x3i83bs11ily5fbktkmxkfg9 UNIQUE (empleado_id);


--
-- Name: empleado uk_elgnbqcwg1gv4h713ytod8n0f; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT uk_elgnbqcwg1gv4h713ytod8n0f UNIQUE (cedula);


--
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: vacuna vacuna_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacuna
    ADD CONSTRAINT vacuna_pkey PRIMARY KEY (id);


--
-- Name: users fk2i8jsoxhy9nyw38lvfykq2p57; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk2i8jsoxhy9nyw38lvfykq2p57 FOREIGN KEY (empleado_id) REFERENCES public.empleado(id);


--
-- Name: user_role fka68196081fvovjhkek5m97n3y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- Name: client_redirect_uris fkai301ylblo02p5381fgie7npr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_redirect_uris
    ADD CONSTRAINT fkai301ylblo02p5381fgie7npr FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: client_authorization_grant_types fkbbgcqer625holyqac4e4s9cfw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_authorization_grant_types
    ADD CONSTRAINT fkbbgcqer625holyqac4e4s9cfw FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: client_authentication_methods fkdd8u924e12nrrg4v9idc87h1o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client_authentication_methods
    ADD CONSTRAINT fkdd8u924e12nrrg4v9idc87h1o FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: vacuna fkedjb13c9bbyqgvmu4m3o7aib9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vacuna
    ADD CONSTRAINT fkedjb13c9bbyqgvmu4m3o7aib9 FOREIGN KEY (empleado_id) REFERENCES public.empleado(id);


--
-- Name: user_role fkj345gk1bovqvfame88rcx7yyx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

