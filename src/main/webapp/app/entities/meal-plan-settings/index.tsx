import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MealPlanSettings from './meal-plan-settings';
import MealPlanSettingsDetail from './meal-plan-settings-detail';
import MealPlanSettingsUpdate from './meal-plan-settings-update';
import MealPlanSettingsDeleteDialog from './meal-plan-settings-delete-dialog';

const MealPlanSettingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MealPlanSettings />} />
    <Route path="new" element={<MealPlanSettingsUpdate />} />
    <Route path=":id">
      <Route index element={<MealPlanSettingsDetail />} />
      <Route path="edit" element={<MealPlanSettingsUpdate />} />
      <Route path="delete" element={<MealPlanSettingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MealPlanSettingsRoutes;
