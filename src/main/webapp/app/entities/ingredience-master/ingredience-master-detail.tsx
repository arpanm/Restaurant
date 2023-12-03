import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ingredience-master.reducer';

export const IngredienceMasterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ingredienceMasterEntity = useAppSelector(state => state.ingredienceMaster.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ingredienceMasterDetailsHeading">
          <Translate contentKey="restaurantApp.ingredienceMaster.detail.title">IngredienceMaster</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ingredienceMasterEntity.id}</dd>
          <dt>
            <span id="ingredience">
              <Translate contentKey="restaurantApp.ingredienceMaster.ingredience">Ingredience</Translate>
            </span>
          </dt>
          <dd>{ingredienceMasterEntity.ingredience}</dd>
        </dl>
        <Button tag={Link} to="/ingredience-master" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ingredience-master/${ingredienceMasterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IngredienceMasterDetail;
