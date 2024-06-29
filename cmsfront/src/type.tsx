export type CourseResponse = {
  id: number;
  code: string;
  title: string;
  season: string;
  year: string;
  instructorId?: number;
  instructorName?: string;
  status: string;
};

export type InstructorResponse = {
  id: number;
  name: string;
  email: string;
  department: string;
  faculty: string;
}