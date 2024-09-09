CREATE TABLE public.users
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT unique_username UNIQUE (username)
);

CREATE INDEX username_idx ON public.users (username);
