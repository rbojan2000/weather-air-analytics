FROM python:3.12-slim

ENV PYTHONFAULTHANDLER=1 \
    PYTHONUNBUFFERED=1 \
    PYTHONHASHSEED=random \
    PIP_NO_CACHE_DIR=off \
    PIP_DISABLE_PIP_VERSION_CHECK=on \
    PIP_DEFAULT_TIMEOUT=100 \
    POETRY_VERSION=1.3.2

RUN pip install "poetry==$POETRY_VERSION"

WORKDIR /opt/aqi-server

COPY poetry.lock pyproject.toml /opt/aqi-server/

RUN POETRY_VIRTUALENVS_CREATE=false \
    poetry install --no-dev --no-root --no-interaction --no-ansi

COPY server /opt/aqi-server/server
COPY schemas /opt/aqi-server/schemas

CMD [ "python3", "-m", "server.main" ]