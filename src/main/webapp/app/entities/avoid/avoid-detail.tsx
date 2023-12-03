import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './avoid.reducer';

export const AvoidDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const avoidEntity = useAppSelector(state => state.avoid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="avoidDetailsHeading">
          <Translate contentKey="restaurantApp.avoid.detail.title">Avoid</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{avoidEntity.id}</dd>
          <dt>
            <span id="avoidIngredience">
              <Translate contentKey="restaurantApp.avoid.avoidIngredience">Avoid Ingredience</Translate>
            </span>
          </dt>
          <dd>{avoidEntity.avoidIngredience}</dd>
        </dl>
        <Button tag={Link} to="/avoid" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/avoid/${avoidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AvoidDetail;
