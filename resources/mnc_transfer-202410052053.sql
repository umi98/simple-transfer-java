--
-- PostgreSQL database dump
--

-- Dumped from database version 14.13 (Ubuntu 14.13-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.13 (Ubuntu 14.13-0ubuntu0.22.04.1)

-- Started on 2024-10-05 20:53:24 WIB

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
-- TOC entry 3402 (class 1262 OID 17522)
-- Name: mnc_transfer; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE mnc_transfer WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE mnc_transfer OWNER TO postgres;

\connect mnc_transfer

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
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3403 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 17598)
-- Name: account_balance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account_balance (
    id character varying(255) NOT NULL,
    balance bigint,
    modified_date timestamp(6) without time zone,
    customer_id character varying(255)
);


ALTER TABLE public.account_balance OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 17605)
-- Name: admin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.admin (
    id character varying(255) NOT NULL,
    full_name character varying(255),
    phone character varying(255),
    user_app_id character varying(255)
);


ALTER TABLE public.admin OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 17612)
-- Name: app_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.app_user (
    id character varying(255) NOT NULL,
    password character varying(255),
    username character varying(255),
    role_id character varying(255)
);


ALTER TABLE public.app_user OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 17619)
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    id character varying(255) NOT NULL,
    full_name character varying(255),
    phone character varying(255),
    user_app_id character varying(255)
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 17626)
-- Name: history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.history (
    id integer NOT NULL,
    activity_time timestamp(6) without time zone,
    description character varying(255),
    app_user_id character varying(255)
);


ALTER TABLE public.history OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 17572)
-- Name: history_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.history_seq OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 17633)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id character varying(255) NOT NULL,
    role character varying(255),
    CONSTRAINT role_role_check CHECK (((role)::text = ANY ((ARRAY['ROLE_ADMIN'::character varying, 'ROLE_CUSTOMER'::character varying])::text[])))
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 3391 (class 0 OID 17598)
-- Dependencies: 210
-- Data for Name: account_balance; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.account_balance VALUES ('26302e39-3661-4017-b111-6ac2b112dc2d', 85000, '2024-10-05 18:25:42.191599', '4b34724c-3fd1-4eb5-b35c-5c263330f7c4');
INSERT INTO public.account_balance VALUES ('cd3dfb59-03bc-4d39-b488-a5f6561135d8', 5000, '2024-10-05 18:34:45.653013', 'f28c3338-e85b-40b1-98fb-282e6cfe552f');


--
-- TOC entry 3392 (class 0 OID 17605)
-- Dependencies: 211
-- Data for Name: admin; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.admin VALUES ('4d2aa9fb-69b0-44c7-a4f0-b055d3c1cfe1', 'abu bakar', '0342-999-8888', '486ee55d-3561-4659-9299-e399efedf2b4');


--
-- TOC entry 3393 (class 0 OID 17612)
-- Dependencies: 212
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.app_user VALUES ('486ee55d-3561-4659-9299-e399efedf2b4', '$2a$10$pjHkncvJFwbD.4KK71mdNexAFSD1gnAWqj9JqkvG0uL6/Dr4g3xvu', 'abu', 'ae770ff7-e57b-486e-a3b7-b4660c3ba49e');
INSERT INTO public.app_user VALUES ('23f68139-2c21-4284-8587-896b3f4dc289', '$2a$10$ewDcLuwO9ZJ65RlFkDZYS.qMD07GVpyJ22VzXG92bO/J7D5EX9PLe', 'mayang', '5b7c65d3-dd52-4454-97cc-0867323e85de');
INSERT INTO public.app_user VALUES ('04a2d64a-48df-4237-8cd7-b92d680e8aa7', '$2a$10$VQhJBpVC1bClA4ZJdD4mLe2jsWoYNtVPd942jUYqdWPhPmmE7dn7G', 'cahyo', '5b7c65d3-dd52-4454-97cc-0867323e85de');


--
-- TOC entry 3394 (class 0 OID 17619)
-- Dependencies: 213
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.customer VALUES ('4b34724c-3fd1-4eb5-b35c-5c263330f7c4', 'mayang', '0342-999-7890', '23f68139-2c21-4284-8587-896b3f4dc289');
INSERT INTO public.customer VALUES ('f28c3338-e85b-40b1-98fb-282e6cfe552f', 'Cahyo', '0342-997-7890', '04a2d64a-48df-4237-8cd7-b92d680e8aa7');


--
-- TOC entry 3395 (class 0 OID 17626)
-- Dependencies: 214
-- Data for Name: history; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.history VALUES (15, '2024-10-05 18:23:55.034411', 'User 486ee55d-3561-4659-9299-e399efedf2b4 just registered as admin', '486ee55d-3561-4659-9299-e399efedf2b4');
INSERT INTO public.history VALUES (16, '2024-10-05 18:24:18.158977', 'User 23f68139-2c21-4284-8587-896b3f4dc289 just registered as customer', '23f68139-2c21-4284-8587-896b3f4dc289');
INSERT INTO public.history VALUES (17, '2024-10-05 18:24:38.226995', 'User 23f68139-2c21-4284-8587-896b3f4dc289 just login', '23f68139-2c21-4284-8587-896b3f4dc289');
INSERT INTO public.history VALUES (18, '2024-10-05 18:25:42.213922', 'User 23f68139-2c21-4284-8587-896b3f4dc289 created an account with id 26302e39-3661-4017-b111-6ac2b112dc2d', '23f68139-2c21-4284-8587-896b3f4dc289');
INSERT INTO public.history VALUES (19, '2024-10-05 18:32:15.676618', 'User 23f68139-2c21-4284-8587-896b3f4dc289 just login', '23f68139-2c21-4284-8587-896b3f4dc289');
INSERT INTO public.history VALUES (20, '2024-10-05 18:32:33.331086', 'User 23f68139-2c21-4284-8587-896b3f4dc289 deposited money, nominal: 90000', '23f68139-2c21-4284-8587-896b3f4dc289');
INSERT INTO public.history VALUES (21, '2024-10-05 18:33:01.625266', 'User 04a2d64a-48df-4237-8cd7-b92d680e8aa7 just registered as customer', '04a2d64a-48df-4237-8cd7-b92d680e8aa7');
INSERT INTO public.history VALUES (22, '2024-10-05 18:34:45.677637', 'User 04a2d64a-48df-4237-8cd7-b92d680e8aa7 created an account with id cd3dfb59-03bc-4d39-b488-a5f6561135d8', '04a2d64a-48df-4237-8cd7-b92d680e8aa7');
INSERT INTO public.history VALUES (23, '2024-10-05 18:35:04.563308', 'User 23f68139-2c21-4284-8587-896b3f4dc289 send money to 04a2d64a-48df-4237-8cd7-b92d680e8aa7totaling: 5000', '23f68139-2c21-4284-8587-896b3f4dc289');
INSERT INTO public.history VALUES (24, '2024-10-05 18:38:21.477608', 'User 23f68139-2c21-4284-8587-896b3f4dc289 check the balance of his account: 26302e39-3661-4017-b111-6ac2b112dc2d', '23f68139-2c21-4284-8587-896b3f4dc289');
INSERT INTO public.history VALUES (25, '2024-10-05 20:26:47.09252', 'User 23f68139-2c21-4284-8587-896b3f4dc289 just login', '23f68139-2c21-4284-8587-896b3f4dc289');


--
-- TOC entry 3396 (class 0 OID 17633)
-- Dependencies: 215
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.role VALUES ('ae770ff7-e57b-486e-a3b7-b4660c3ba49e', 'ROLE_ADMIN');
INSERT INTO public.role VALUES ('5b7c65d3-dd52-4454-97cc-0867323e85de', 'ROLE_CUSTOMER');


--
-- TOC entry 3404 (class 0 OID 0)
-- Dependencies: 209
-- Name: history_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.history_seq', 25, true);


--
-- TOC entry 3229 (class 2606 OID 17604)
-- Name: account_balance account_balance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_balance
    ADD CONSTRAINT account_balance_pkey PRIMARY KEY (id);


--
-- TOC entry 3233 (class 2606 OID 17611)
-- Name: admin admin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admin_pkey PRIMARY KEY (id);


--
-- TOC entry 3237 (class 2606 OID 17618)
-- Name: app_user app_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);


--
-- TOC entry 3239 (class 2606 OID 17625)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 3243 (class 2606 OID 17632)
-- Name: history history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_pkey PRIMARY KEY (id);


--
-- TOC entry 3245 (class 2606 OID 17640)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 3235 (class 2606 OID 17644)
-- Name: admin uk9ic49vp5dn35xcg0r8ludr52j; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin
    ADD CONSTRAINT uk9ic49vp5dn35xcg0r8ludr52j UNIQUE (user_app_id);


--
-- TOC entry 3241 (class 2606 OID 17646)
-- Name: customer ukcy18jc055f909t387cif3ce1o; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT ukcy18jc055f909t387cif3ce1o UNIQUE (user_app_id);


--
-- TOC entry 3231 (class 2606 OID 17642)
-- Name: account_balance ukfrbkjwyu6wc3eca0eew4l1p2s; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_balance
    ADD CONSTRAINT ukfrbkjwyu6wc3eca0eew4l1p2s UNIQUE (customer_id);


--
-- TOC entry 3248 (class 2606 OID 17657)
-- Name: app_user fk49hx9nj6onfot1fxtonj986ab; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT fk49hx9nj6onfot1fxtonj986ab FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 3247 (class 2606 OID 17652)
-- Name: admin fk76px2272giik16jg86ummwvbo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin
    ADD CONSTRAINT fk76px2272giik16jg86ummwvbo FOREIGN KEY (user_app_id) REFERENCES public.app_user(id);


--
-- TOC entry 3250 (class 2606 OID 17667)
-- Name: history fkdev51v6ij6c3xs38umxp374cx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT fkdev51v6ij6c3xs38umxp374cx FOREIGN KEY (app_user_id) REFERENCES public.app_user(id);


--
-- TOC entry 3249 (class 2606 OID 17662)
-- Name: customer fkjlqo8piw4s7yh3iff897ctv51; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT fkjlqo8piw4s7yh3iff897ctv51 FOREIGN KEY (user_app_id) REFERENCES public.app_user(id);


--
-- TOC entry 3246 (class 2606 OID 17647)
-- Name: account_balance fkq6gvcon3xktpi2ifh1jjs91xi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account_balance
    ADD CONSTRAINT fkq6gvcon3xktpi2ifh1jjs91xi FOREIGN KEY (customer_id) REFERENCES public.customer(id);


-- Completed on 2024-10-05 20:53:24 WIB

--
-- PostgreSQL database dump complete
--

