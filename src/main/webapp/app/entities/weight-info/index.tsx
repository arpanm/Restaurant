import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WeightInfo from './weight-info';
import WeightInfoDetail from './weight-info-detail';
import WeightInfoUpdate from './weight-info-update';
import WeightInfoDeleteDialog from './weight-info-delete-dialog';

const WeightInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WeightInfo />} />
    <Route path="new" element={<WeightInfoUpdate />} />
    <Route path=":id">
      <Route index element={<WeightInfoDetail />} />
      <Route path="edit" element={<WeightInfoUpdate />} />
      <Route path="delete" element={<WeightInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WeightInfoRoutes;
