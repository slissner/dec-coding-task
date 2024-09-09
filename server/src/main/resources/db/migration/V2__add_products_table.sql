CREATE TABLE public.products
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at TIMESTAMP        DEFAULT now() NOT NULL,
    updated_at TIMESTAMP,
    name       VARCHAR(255)                   NOT NULL,
    price      NUMERIC(10, 2),
    CONSTRAINT unique_name UNIQUE (name)
);
