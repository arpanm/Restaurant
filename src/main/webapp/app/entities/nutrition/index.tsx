import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nutrition from './nutrition';
import NutritionDetail from './nutrition-detail';
import NutritionUpdate from './nutrition-update';
import NutritionDeleteDialog from './nutrition-delete-dialog';

const NutritionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nutrition />} />
    <Route path="new" element={<NutritionUpdate />} />
    <Route path=":id">
      <Route index element={<NutritionDetail />} />
      <Route path="edit" element={<NutritionUpdate />} />
      <Route path="delete" element={<NutritionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NutritionRoutes;
