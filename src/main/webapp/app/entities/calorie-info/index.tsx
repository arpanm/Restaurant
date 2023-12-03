import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CalorieInfo from './calorie-info';
import CalorieInfoDetail from './calorie-info-detail';
import CalorieInfoUpdate from './calorie-info-update';
import CalorieInfoDeleteDialog from './calorie-info-delete-dialog';

const CalorieInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CalorieInfo />} />
    <Route path="new" element={<CalorieInfoUpdate />} />
    <Route path=":id">
      <Route index element={<CalorieInfoDetail />} />
      <Route path="edit" element={<CalorieInfoUpdate />} />
      <Route path="delete" element={<CalorieInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CalorieInfoRoutes;
