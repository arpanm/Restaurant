import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MealPlan from './meal-plan';
import MealPlanDetail from './meal-plan-detail';
import MealPlanUpdate from './meal-plan-update';
import MealPlanDeleteDialog from './meal-plan-delete-dialog';

const MealPlanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MealPlan />} />
    <Route path="new" element={<MealPlanUpdate />} />
    <Route path=":id">
      <Route index element={<MealPlanDetail />} />
      <Route path="edit" element={<MealPlanUpdate />} />
      <Route path="delete" element={<MealPlanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MealPlanRoutes;
