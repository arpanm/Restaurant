import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './calorie-info.reducer';

export const CalorieInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const calorieInfoEntity = useAppSelector(state => state.calorieInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="calorieInfoDetailsHeading">
          <Translate contentKey="restaurantApp.calorieInfo.detail.title">CalorieInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{calorieInfoEntity.id}</dd>
          <dt>
            <span id="dailyCalorieLimit">
              <Translate contentKey="restaurantApp.calorieInfo.dailyCalorieLimit">Daily Calorie Limit</Translate>
            </span>
          </dt>
          <dd>{calorieInfoEntity.dailyCalorieLimit}</dd>
        </dl>
        <Button tag={Link} to="/calorie-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/calorie-info/${calorieInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CalorieInfoDetail;
