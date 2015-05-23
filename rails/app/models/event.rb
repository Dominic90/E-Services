class Event < ActiveRecord::Base
	belongs_to :user
	belongs_to :connection
	validates :title, presence: true,
                	  length: { minimum: 5 }
end
