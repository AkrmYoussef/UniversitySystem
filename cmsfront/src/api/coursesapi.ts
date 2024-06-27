import axios from "axios";
import { CourseResponse } from "../type";

export const getCourses = async (): Promise<CourseResponse[]> => {
  const response = await axios.get(
    `${import.meta.env.VITE_API_URL}/api/courses/getCourses`
  );
  return response.data;
};

export const deleteCourse = async (id: number): Promise<void> => {
  await axios.delete(
    `${import.meta.env.VITE_API_URL}/api/courses/deleteCourse/${id}`
  );
};

export const addCourse = async ( course: CourseResponse ): Promise<CourseResponse> => {
  const response = await axios.post(
    `${import.meta.env.VITE_API_URL}/api/courses/createCourse`,
    course,
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  return response.data;
};

export const updateCourse = async ( course: CourseResponse ): Promise<CourseResponse> => {
  const response = await axios.put(
    `${import.meta.env.VITE_API_URL}/api/courses/updateCourse/${course.id}`,
    course,
    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  return response.data;
};
