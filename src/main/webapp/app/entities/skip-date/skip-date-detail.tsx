import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './skip-date.reducer';

export const SkipDateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const skipDateEntity = useAppSelector(state => state.skipDate.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="skipDateDetailsHeading">
          <Translate contentKey="restaurantApp.skipDate.detail.title">SkipDate</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{skipDateEntity.id}</dd>
          <dt>
            <span id="skipDate">
              <Translate contentKey="restaurantApp.skipDate.skipDate">Skip Date</Translate>
            </span>
          </dt>
          <dd>
            {skipDateEntity.skipDate ? <TextFormat value={skipDateEntity.skipDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="restaurantApp.skipDate.mealPlanItem">Meal Plan Item</Translate>
          </dt>
          <dd>{skipDateEntity.mealPlanItem ? skipDateEntity.mealPlanItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/skip-date" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/skip-date/${skipDateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SkipDateDetail;
